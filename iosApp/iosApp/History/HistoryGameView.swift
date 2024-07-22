//
//  HistoryGameView.swift
//  iosApp
//
//  Created by Thomas Bernard on 22/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HistoryGameView: View {
    
    let game : GameModel
    
    var body: some View {
        Form {
            Section("Résultats"){
                GameResultView(scores: game.calculateScore())
            }
            Section("Tours"){
                List(game.rounds, id: \.id){ round in
                    RoundListItemView(round: round)
                }
            }
        }
        .navigationTitle("Partie du ")
        .toolbar {
            Button(action : {}) {
                Label("Reprendre", systemImage: "play.circle")
            }
            Button(action : {}) {
                Label("Supprimer", systemImage: "trash")
            }
        }
    }
}
