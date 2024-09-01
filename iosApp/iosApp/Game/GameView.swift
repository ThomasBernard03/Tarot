//
//  GameView.swift
//  iosApp
//
//  Created by Thomas Bernard on 16/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct GameView: View {
    
    @State private var viewModel = ViewModel()
    
    @State private var showNewGameSheet = false
    @State private var showNewRoundSheet = false
    @State private var showFinishGameConfirmationAlert : Bool = false
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .center) {
                
                if let game = viewModel.currentGame {
                    Form {
                        Section("Résultats"){
                            GameResultView(scores: game.calculateScore())
                        }
                        
                        Section("Tours"){
                            List {
                                ForEach(game.rounds, id : \.id){ round in
                                    let index = (game.rounds.firstIndex(of: round) ?? 0) + 1
                                    NavigationLink(destination: RoundDetailView(index: index, round: round)) {
                                        RoundListItemView(round: round, numberOfPlayers: game.players.count)
                                    }
                                    .swipeActions(edge:.trailing){
                                        Button(action: { viewModel.deleteRound(round: round) }){
                                            Label("Supprimer le tour", systemImage: "trash")
                                        }
                                        .tint(.red)
                                        
                                        Button(action: { }){
                                            Label("Modifier le tour", systemImage: "pencil")
                                        }
                                        .tint(.accentColor)
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    Text("Aucune partie en cours, appuyez sur le '+' pour démarrer une nouvelle partie")
                        .padding()
                        .multilineTextAlignment(.center)
                }
            }
            .navigationTitle("Partie en cours")
            .toolbar {
                if viewModel.currentGame == nil {
                    Button(action: { showNewGameSheet.toggle() }) {
                        Label("New game", systemImage: "plus").labelStyle(.iconOnly)
                    }
                }
                else {
                    Button(action: { showFinishGameConfirmationAlert.toggle() }) {
                        Label("Terminer la partie", systemImage: "flag.checkered").labelStyle(.iconOnly)
                    }
                    
                    Button(action: { showNewRoundSheet.toggle()}) {
                        Label("New round", systemImage: "plus").labelStyle(.iconOnly)
                    }
                }
            }
            .sheet(isPresented: $showNewGameSheet) {
                NewGameSheet(players: $viewModel.players) { players in
                    viewModel.createGame(players: players)
                    showNewGameSheet.toggle()
                }
                .onAppear { viewModel.getPlayers() }
            }
            .sheet(isPresented: $showNewRoundSheet){
                NewRoundSheet(players:viewModel.currentGame!.players){ taker, bid, calledPlayer, oudlers, points in
                    viewModel.createRound(taker: taker, bid: bid, calledPlayer: calledPlayer, oudlers: oudlers, points: points)
                    showNewRoundSheet = false
                }
            }
            .onAppear { viewModel.getCurrentGame() }
            .alert("Voulez-vous terminer la partie ?", isPresented: $showFinishGameConfirmationAlert){
                Button("Terminer", role:.destructive) { viewModel.finishGame() }
                Button("Annuler", role: .cancel) { }
            }
        }
    }
}

#Preview {
    GameView()
}
