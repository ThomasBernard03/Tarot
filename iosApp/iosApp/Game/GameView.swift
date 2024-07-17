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
    
    private let getPlayersUseCase = GetPlayersUseCase()
    private let getCurrentGameUseCase = GetCurrentGameUseCase()
    private let createGameUseCase = CreateGameUseCase()
    private let finishGameUseCase = FinishGameUseCase()
    
    @State private var showNewGameSheet = false
    @State private var players: [PlayerModel] = []
    @State private var selectedPlayers = Set<PlayerModel>()
    @State private var currentGame: GameModel? = nil
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .center) {
                if currentGame == nil {
                    Text("Aucune partie en cours, appuyez sur le '+' pour démarrer une nouvelle partie")
                        .padding()
                        .multilineTextAlignment(.center)
                } else {
                    Grid {
                        GridRow() {
                            ForEach(currentGame!.players, id:\.id){ player in
                                Text(player.name)
                                    .onTapGesture {
                                        print(player.name + " tapped")
                                    }
                            }
                        }
                        Divider()
                        ForEach(currentGame!.rounds, id:\.id){ round in
                            GridRow {
                                Text(round.bid.name)
                            }
                        }
                    }
                    .padding(.top)
                    Spacer()
                }
            }
            .navigationTitle("Partie en cours")
            .toolbar {
                if currentGame == nil {
                    Button(action: {
                        selectedPlayers = []
                        showNewGameSheet.toggle()
                    }) {
                        Label("New game", systemImage: "plus")
                            .labelStyle(.iconOnly)
                    }
                }
                else {
                    Button(action: {
                        finishGameUseCase.invoke(gameId: currentGame!.id) { result, _ in
                            if result?.isSuccess() ?? false {
                                currentGame = nil
                            }
                        }
                        
                    }) {
                        Label("Terminer la partie", systemImage: "flag.checkered")
                            .labelStyle(.iconOnly)
                    }
                }
            }
            .sheet(isPresented: $showNewGameSheet) {
                NewGameSheet(players: $players, selectedPlayers: $selectedPlayers) {
                    createGameUseCase.invoke(players: Array(selectedPlayers)) { result, _ in
                        if result?.isSuccess() ?? false {
                            currentGame = result?.getOrNull()
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
            .onAppear {
                getCurrentGameUseCase.invoke { result, _ in
                    if result?.isSuccess() ?? false {
                        currentGame = result?.getOrNull()
                    }
                }
            }
        }
    }
}

#Preview {
    GameView()
}
