import SwiftUI
import Shared

struct PlayersView: View {
    @State private var players: [PlayerModel] = []
    @State private var error: String?

    var body: some View {
        VStack {
            if let error = error {
                Text("Error: \(error)")
                    .foregroundColor(.red)
            } else {
                List(players, id: \.id) { player in
                    Text(player.name)
                }
                
                
                Button {
                    let createPlayerUseCase = CreatePlayerUseCase()
                    let player = CreatePlayerModel(name: "Joueur " + String(players.count), color: .red)
                    createPlayerUseCase.invoke(player: player){ result, error in
                        players.append(result!)
                    }
                } label: {
                    Label("Create a player", systemImage: "list.dash")
                }

            }
        }
        .onAppear {
            let getPlayersUseCase = GetPlayersUseCase()
            getPlayersUseCase.execute { result, error in
                players = result ?? []
            }
        }
    }
}

#Preview {
    PlayersView()
}
