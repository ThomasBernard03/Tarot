//
//  HistoryView.swift
//  iosApp
//
//  Created by Thomas Bernard on 16/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HistoryView: View {
    private let getGameHistoryUseCase = GetGameHistoryUseCase()
    @State private var games: [GameModel] = []
    
    var body: some View {
        NavigationView {
            VStack(alignment:.center) {
                if games.isEmpty {
                    Text("Aucune partie réalisée")
                }
                else {
                    List(games, id: \.id) { game in
                        Text(game.startedAt.time.description())
                    }
                }
            }
            .navigationTitle("Historique")
            .onAppear {
                getGameHistoryUseCase.invoke { result, _ in
                    if result?.isSuccess() ?? false {
                        games = result?.getOrNull() as! [GameModel]
                    }
                }
            }
        }
    }
}

#Preview {
    HistoryView()
}
