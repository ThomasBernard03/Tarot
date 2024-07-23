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
    
    private let getPlayersUseCase = GetPlayersUseCase()
    
    private let createGameUseCase = CreateGameUseCase()
    private let finishGameUseCase = FinishGameUseCase()
    private let createRoundUseCase = CreateRoundUseCase()
    
    @State private var showNewGameSheet = false
    @State private var showNewRoundSheet = false
    @State private var players: [PlayerModel] = []
    @State private var selectedPlayers = Set<PlayerModel>()
    @State private var showFinishGameConfirmationAlert : Bool = false
    

    
    var body: some View {
        NavigationStack {
            VStack(alignment: .center) {
                if viewModel.currentGame == nil {
                    Text("Aucune partie en cours, appuyez sur le '+' pour démarrer une nouvelle partie")
                        .padding()
                        .multilineTextAlignment(.center)
                } else {
                    Form {
                        Section("Résultats"){
                            GameResultView(scores: viewModel.currentGame!.calculateScore())
                        }
                        
                        Section("Tours"){
                            List(viewModel.currentGame!.rounds, id: \.id){ round in
                                NavigationLink(destination: EmptyView()) {
                                    RoundListItemView(round: round)
                                }
                            }
                        }
                    }
                }
            }
            .navigationTitle("Partie en cours")
            .toolbar {
                if viewModel.currentGame == nil {
                    Button(action: {
                        selectedPlayers = []
                        showNewGameSheet.toggle()
                    }) {
                        Label("New game", systemImage: "plus")
                            .labelStyle(.iconOnly)
                    }
                }
                else {
                    Button(action: { showFinishGameConfirmationAlert.toggle() }) {
                        Label("Terminer la partie", systemImage: "flag.checkered")
                            .labelStyle(.iconOnly)
                    }
                    
                    Button(
                        action: {
                            showNewRoundSheet.toggle()
                        }
                    ) {
                        Label("New round", systemImage: "plus")
                            .labelStyle(.iconOnly)
                    }
                }
            }
            .sheet(isPresented: $showNewGameSheet) {
                NewGameSheet(players: $players, selectedPlayers: $selectedPlayers) {
                    createGameUseCase.invoke(players: Array(selectedPlayers)) { result, _ in
                        if result?.isSuccess() ?? false {
                            viewModel.currentGame = result?.getOrNull()
                        }
                    }
                    
                    showNewGameSheet.toggle()
                }
                .onAppear {
                    getPlayersUseCase.invoke { result, _ in
                        if result?.isSuccess() ?? false {
                            players = result?.getOrNull() as! [PlayerModel]
                        }
                    }
                }
            }
            .sheet(isPresented: $showNewRoundSheet){
                
                NewRoundSheet(players:viewModel.currentGame!.players){ taker, bid, calledPlayer, oudlers, points in
                    createRoundUseCase.invoke(gameId: viewModel.currentGame!.id, taker: taker, playerCalled: calledPlayer, bid: bid, oudlers: oudlers, points: Int32(points)) { result, _ in
                        if result?.isSuccess() ?? false {
                            viewModel.getCurrentGame()
                            showNewRoundSheet = false
                        }
                    }
                }
 
            }
            .onAppear { viewModel.getCurrentGame() }
            .alert("Voulez-vous terminer la partie ?", isPresented: $showFinishGameConfirmationAlert){
                Button("Terminer", role:.destructive){
                    finishGameUseCase.invoke(gameId: viewModel.currentGame!.id) { result, _ in
                        if result?.isSuccess() ?? false {
                            viewModel.currentGame = nil
                        }
                    }
                }
                Button("Annuler", role: .cancel) { }
            }
        }
    }
}

#Preview {
    GameView()
}
