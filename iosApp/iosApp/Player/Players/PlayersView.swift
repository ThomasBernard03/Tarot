import SwiftUI
import Toast
import Shared

struct PlayersView: View {
    
    @State private var viewModel = ViewModel()
    
    @State private var showNewPlayerSheet = false
    @State private var playerName : String = ""
    @State private var selectedColor : PlayerColor? = nil

    var body: some View {
        NavigationStack {
            VStack(alignment:.center) {
                if viewModel.players.isEmpty {
                    Text("Créez un nouveau joueur pour commencer")
                }
                else {
                    List {
                        ForEach(viewModel.players, id:\.id){ player in
                            NavigationLink(destination: PlayerView(id: player.id, name: player.name)) {
                                Label(
                                    title: { Text(player.name) },
                                    icon: { Image(systemName: "circle.fill").foregroundColor(player.color.toColor()) }
                                )
                            }
                            .swipeActions(edge:.trailing){
                                Button(action: { viewModel.deletePlayer(player: player)}){
                                    Label("Supprimer le joueur", systemImage: "trash")
                                }
                                .tint(.red)
                            }
                        }
                    }
                }
            }
            .navigationTitle("Joueurs")
            .toolbar {
                Button(
                    action: {
                        playerName = ""
                        selectedColor = nil
                        showNewPlayerSheet.toggle()
                    }
                ){
                    Label("Add player", systemImage: "plus")
                        .labelStyle(.iconOnly)
                }
            }
            .onAppear { viewModel.getPlayers() }
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
                        viewModel.createPlayer(name: playerName, color: selectedColor!)
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
