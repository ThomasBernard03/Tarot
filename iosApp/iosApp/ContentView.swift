import SwiftUI
import Shared

struct ContentView: View {
    var body: some View {
        TabView {
            GameView()
                .tabItem { Label("Jouer", systemImage: "menucard") }
            
            HistoryView()
                .tabItem { Label("Historique", systemImage: "list.bullet") }
            
            
            PlayersView()
            .tabItem { Label("Joueurs", systemImage: "person.3.sequence") }
            
            
            SettingsView()
            .tabItem { Label("Informations", systemImage: "info.circle") }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
