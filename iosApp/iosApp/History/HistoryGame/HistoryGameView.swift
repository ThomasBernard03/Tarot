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
    @State private var viewModel = ViewModel()
    @State private var showDeleteGameAlert = false
    @State private var showResumeGameAlert = false
    
    var body: some View {
        Form {
            Section("Résultats"){
                GameResultView(scores: game.calculateScore())
            }
            Section("Tours"){
                List(game.rounds, id: \.id){ round in
                    RoundListItemView(round: round, numberOfPlayers: game.players.count)
                }
            }
        }
        .navigationTitle("Partie du " + String(game.startedAt.date.dayOfMonth) + " " + game.startedAt.month.name.lowercased())
        .toolbar {
            Button(action : { showResumeGameAlert.toggle() }) {
                Label("Reprendre", systemImage: "play.circle")
            }
            Button(action : { showDeleteGameAlert.toggle() }) {
                Label("Supprimer", systemImage: "trash")
            }
        }
        .alert("Voulez-vous supprimer cette partie ?", isPresented: $showDeleteGameAlert){
            Button("Supprimer", role:.destructive){
                viewModel.deleteGame(game: game)
                showDeleteGameAlert.toggle()
            }
            Button("Annuler", role: .cancel) { }
        }
        .alert(
            "Reprendre cette partie ?",
            isPresented: $showResumeGameAlert,
            actions: {
                Button("Reprendre"){
                    viewModel.resumeGame(game: game)
                    showResumeGameAlert.toggle()
                }
                Button("Annuler", role: .cancel) { }
        }, message: {
            Text("Si une partie est en cours, cette dernière sera terminée")
        })
    }
}
