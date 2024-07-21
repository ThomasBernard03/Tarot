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
    private let createRoundUseCase = CreateRoundUseCase()
    
    @State private var showNewGameSheet = false
    @State private var showNewRoundSheet = false
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
                    Form {
                        Section("Résultats"){
                            List {
                                ForEach(currentGame!.calculateScore().sorted { Int($0.second!) > Int($1.second!) }, id: \.first!.id) { playerScore in
                                    
                                    let index = currentGame!.calculateScore().sorted { Int($0.second!) > Int($1.second!) }.firstIndex { $0.first!.id == playerScore.first!.id }!
                                    
                                    HStack {
                                        ZStack {
                                            Circle()
                                                .foregroundColor(playerScore.first!.color.toColor())
                                                .frame(width: 25, height: 25)
                                            Text("" + playerScore.first!.name.uppercased().prefix(1))
                                                .foregroundColor(.white)
                                                .font(.system(size: 12))
                                        }
                                        
                                        Text(playerScore.first!.name)
                                        
                                        Spacer()
                                        
                                        if index == 0 {
                                            Image(systemName: "medal.fill")
                                                .foregroundColor(.yellow)
                                        } else if index == 1 {
                                            Image(systemName: "medal.fill")
                                                .foregroundColor(.gray)
                                        } else if index == 2 {
                                            Image(systemName: "medal.fill")
                                                .foregroundColor(.brown)
                                        }
             
                                        
                                        Text(String(Int(playerScore.second!)))
                                            .foregroundColor(Int(playerScore.second!) >= 0 ? Color.green : Color.red)
                                    }
                                }
                            }
                        }
                        
                        Section("Tours"){
                            List(currentGame!.rounds, id: \.id){ round in
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
                    Button(
                        action: {
                        finishGameUseCase.invoke(gameId: currentGame!.id) { result, _ in
                            if result?.isSuccess() ?? false {
                                currentGame = nil
                            }
                        }
                        
                        }
                    ) {
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
            .sheet(isPresented: $showNewRoundSheet){
                
                NewRoundSheet(players:currentGame!.players){ taker, bid, calledPlayer, oudlers, points in
                    createRoundUseCase.invoke(gameId: currentGame!.id, taker: taker, playerCalled: calledPlayer, bid: bid, oudlers: oudlers, points: Int32(points)) { result, _ in
                        if result?.isSuccess() ?? false {
                            
                            let newRounds = currentGame!.rounds + [result?.getOrNull()!]
                            //currentGame?.rounds = newRounds
                            showNewRoundSheet = false
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
