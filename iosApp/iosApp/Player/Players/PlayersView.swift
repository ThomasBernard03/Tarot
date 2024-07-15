import SwiftUI
import Shared

struct PlayersView: View {
    @State private var players: [PlayerModel] = []
    @State private var error: String?
    
    @State private var showNewPlayerSheet = false
    @State private var playerName : String = ""
    

    var body: some View {
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
                players.remove(atOffsets: index)
            }
        }
        .navigationTitle("Joueurs")
        .toolbar {
            Button(action: {
                playerName = ""
                showNewPlayerSheet.toggle()
            }){
                Label("Add player", systemImage: "plus")
                    .labelStyle(.iconOnly)
            }
       
        }
        .onAppear {
            let getPlayersUseCase = GetPlayersUseCase()
            getPlayersUseCase.invoke { result, error in

                players = result?.getOrNull() as! [PlayerModel]
            }
        }
        .sheet(isPresented: $showNewPlayerSheet){
            VStack {
                TextField("Nom du joueur", text: $playerName)
                    .textFieldStyle(.roundedBorder)
                
                Button("Créer le joueur"){
                    let createPlayerUseCase = CreatePlayerUseCase()
                    let playerColor : PlayerColor = PlayerColor.all().randomElement()!
                    let player = CreatePlayerModel(name: playerName, color: playerColor)
                    createPlayerUseCase.invoke(player: player){ result, error in
                        players.append((result?.getOrNull()!)!)
                    }
                    showNewPlayerSheet.toggle()
                    
                }
                .buttonStyle(.borderedProminent)
            }
            .padding()
            .presentationDetents([.height(200)])
        }
        
    }
}

#Preview {
    PlayersView()
}
