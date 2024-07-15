import SwiftUI
import Shared

struct ContentView: View {
    var body: some View {
        TabView {
            NavigationStack {
                PlayersView()
            }
            
            .tabItem { Label("Players", systemImage: "list.dash") }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
