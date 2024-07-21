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
                    VStack {
                        
                        List(currentGame!.rounds, id: \.id){ round in
                            NavigationLink(destination: EmptyView()) {
                                HStack {
                                    ZStack(alignment:.bottomTrailing) {
                                        ZStack {
                                            Circle()
                                                .foregroundColor(round.taker.color.toColor())
                                                .frame(width: 40, height: 40)
                                            Text("" + round.taker.name.uppercased().prefix(1))
                                                .foregroundColor(.white)
                                        }.padding(.trailing, 5)
                                        
                                        if(round.calledPlayer != nil && round.taker != round.calledPlayer) {
                                            ZStack {
                                                Circle()
                                                    .foregroundColor(round.calledPlayer!.color.toColor())
                                                    .frame(width: 25, height: 25)
                                                Text("" + round.calledPlayer!.name.uppercased().prefix(1))
                                                    .foregroundColor(.white)
                                                    .font(.system(size: 12))
                                            }
                                            .shadow(radius: 1)
                                        }
                                       
                                    }
                                    // Text(round.bi)
                                    
                                    ForEach(round.oudlers, id: \.self){ oudler in
                                        OudlerView(oudler: oudler)
                                    }
                                    
                                    Text(round.bid.toLabel())
                                    
                                    
                                    Spacer()
                                    
                                    VStack(alignment:.trailing) {
                                        let takerScore = ConstantsKt.calculateTakerScore(points: round.points, bid: round.bid, oudlers: Int32(round.oudlers.count), calledHimSelf: round.calledPlayer == round.taker)
                                        
                                        
                                        Text(String(takerScore))
                                            .foregroundColor(takerScore >= 0 ? Color.green : Color.red)
                                            .padding(.trailing, 5)
                                            .fontWeight(.bold)
                                        
                                        if (round.calledPlayer != nil && round.calledPlayer != round.taker){
                                            let calledPlayerScore = ConstantsKt.calculatePartnerScore(takerScore: takerScore)
                                            
                                            Text(String(calledPlayerScore))
                                                .foregroundColor(calledPlayerScore >= 0 ? Color.green : Color.red)
                                                .padding(.trailing, 5)
                                                .font(.system(size: 12))
                                    }
                                    
                     
                                    }
                                    
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
