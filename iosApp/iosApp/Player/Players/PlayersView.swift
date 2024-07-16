import SwiftUI
import Shared

struct PlayersView: View {
    private let getPlayersUseCase = GetPlayersUseCase()
    private let createPlayerUseCase = CreatePlayerUseCase()
    private let deletePlayerUseCase = DeletePlayerUseCase()
    
    @State private var players: [PlayerModel] = []
    @State private var error: String?
    
    @State private var showNewPlayerSheet = false
    @State private var playerName : String = ""
    @State private var selectedColor : PlayerColor? = nil

    var body: some View {
        NavigationStack {
            VStack(alignment:.center) {
                if players.isEmpty {
                    Text("Créez un nouveau joueur pour commencer")
                }
                else {
                    List {
                        ForEach(players, id:\.id){ player in
                            NavigationLink(destination: PlayerView(id: player.id, name: player.name)) {
                                Label(
                                    title: { Text(player.name) },
                                    icon: { Image(systemName: "circle.fill").foregroundColor(player.color.toColor()) }
                                )
                            }
                        }
                        .onDelete { index in
                            let player = players[index.first!]
                            deletePlayerUseCase.invoke(id: player.id) { result, _ in
                                players.remove(atOffsets: index)
                            }
                        }
                    }
                }
            }
            .navigationTitle("Joueurs")
            .toolbar {
                Button(action: {
                    playerName = ""
                    selectedColor = nil
                    showNewPlayerSheet.toggle()
                }){
                    Label("Add player", systemImage: "plus")
                        .labelStyle(.iconOnly)
                }
           
            }
            .onAppear {
                getPlayersUseCase.invoke { result, error in
                    if result?.isSuccess() ?? false {
                        players = result?.getOrNull() as! [PlayerModel]
                    }
                }
            }
            .sheet(isPresented: $showNewPlayerSheet){
                VStack(alignment:.leading, spacing: 12) {
                    Text("Création d'un joueur")
                        .font(.title2)
                        .padding()
                    
                    TextField("Nom du joueur", text: $playerName)
                        .textFieldStyle(.roundedBorder)
                        .padding(.horizontal)
                    
                    ScrollView(.horizontal) {
                        HStack {
                            ForEach(PlayerColor.all(), id: \.self) { color in
                                Circle()
                                    .foregroundColor(color.toColor())
                                    .frame(width: 50, height: 50)
                                    .overlay(
                                        Circle().stroke(Color.primary, lineWidth: selectedColor == color ? 4 : 0)
                                    )
                                    .onTapGesture {
                                        if selectedColor == color {
                                            selectedColor = nil
                                        }
                                        else {
                                            selectedColor = color
                                        }
                                    }
                            }
                        }
                        .padding()
                    }
                    
                    Button(action: {
                        let player = CreatePlayerModel(name: playerName, color: selectedColor!)
                        createPlayerUseCase.invoke(player: player){ result, _ in
                            
                            if result?.isSuccess() ?? false {
                                players.append((result?.getOrNull()!)!)
                            }
                           
                        }
                        showNewPlayerSheet.toggle()
                    }){
                        Text("Créer le joueur")
                            .frame(maxWidth: .infinity)
                    }
                    .buttonStyle(.borderedProminent)
                    .controlSize(.large)
                    .disabled(playerName.isEmpty || selectedColor == nil)
                    .padding([.horizontal, .bottom])
                }
                .presentationDetents([.height(300)])
            }
            
        }
    }
}

#Preview {
    PlayersView()
}
