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
    
    @State private var taker: PlayerModel? = nil
    @State private var bid: Bid = Bid.small
    @State private var calledPlayer: PlayerModel? = nil
    @State private var oudler: Oudler? = nil
    @State private var points: Double = 0
    @State private var attackSide: Bool = true
    
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
                            HStack {
                                // Text(round.bi)
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
                
                
                
                VStack(alignment: .leading, spacing: 12) {
                    Text("Nouveau tour")
                        .font(.title2)
                        .padding()
                    
                    Form {
                        Section("General") {
                            Picker("Preneur",selection: $taker) {
                                Text("").tag(Optional<PlayerModel>(nil))
                                ForEach(currentGame!.players, id: \.id) {
                                    Text($0.name).tag(Optional($0))
                               }
                             }.pickerStyle(.menu)
                            
                            Picker("Contrat",selection: $bid) {
                                ForEach(Bid.all(), id: \.self) {
                                    Text($0.name).tag($0)
                                }
                             }.pickerStyle(.menu)
           
                            
                            if(currentGame!.players.count >= 5){
                                Picker("Joueur appelé",selection: $calledPlayer) {
                                    Text("").tag(Optional<PlayerModel>(nil))
                                    ForEach(currentGame!.players, id: \.id) {
                                        Text($0.name).tag(Optional($0))
                                    }
                                 }.pickerStyle(.menu)
                            }
                            
                            Picker("Bouts",selection: $oudler) {
                                Text("").tag(Optional<Oudler>(nil))
                                ForEach(Oudler.all(), id: \.self) {
                                    Text($0.name).tag(Optional($0))
                                }
                             }.pickerStyle(.menu)
                            
                            
                        }
                        
                        Section("Points"){
                            Group {
                                VStack {
                                    Picker("",selection: $attackSide) {
                                        Text("Attaque").tag(true)
                                        Text("Défense").tag(false)
                                    }.pickerStyle(.segmented)
                                    
                                    Text(String(points))
                                        .font(.title)
                                    
                                    
                                    Slider(value: $points, in: 0...91)
                                }
                            }
                        }
                        
                        
                        
                        Section("Bonus"){
                            
                        }
                    }
                    

                    

                    Button(action: {
                        createRoundUseCase.invoke(gameId: currentGame!.id, taker: taker!, playerCalled: calledPlayer, bid: bid, oudlers: [oudler!], points: 51){ result, error in
                            
                        }
                    }) {
                        Text("Ajouter le tour")
                            .frame(maxWidth: .infinity)
                    }
                    .buttonStyle(.borderedProminent)
                    .controlSize(.large)
                    .disabled(taker == nil)
                    .padding([.horizontal, .bottom])
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
