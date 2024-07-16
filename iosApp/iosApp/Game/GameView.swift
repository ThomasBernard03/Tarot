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
                    // Votre code existant ici
                }
            }
            .navigationTitle("Partie en cours")
            .toolbar {
                Button(action: {
                    selectedPlayers = []
                    showNewGameSheet.toggle()
                }) {
                    Label("New game", systemImage: "plus")
                        .labelStyle(.iconOnly)
                }
            }
            .sheet(isPresented: $showNewGameSheet) {
                VStack(alignment: .leading, spacing: 12) {
                    Text("Nouvelle partie")
                        .font(.title2)
                        .padding()
                    
                    Text("Sélectionner les joueurs à ajouter dans la partie")
                        .font(.title3)
                        .padding(.horizontal)
                    
                    List(players, id: \.id) { player in
                        HStack(spacing: 12) {
                            Image(systemName: selectedPlayers.contains(player) ? "checkmark.circle.fill" : "circle")
                                .foregroundColor(selectedPlayers.contains(player) ? player.color.toColor() : .gray)
                            Text(player.name)
                            Spacer()
                        }
                        .contentShape(Rectangle())
                        .onTapGesture {
                            if selectedPlayers.contains(player) {
                                selectedPlayers.remove(player)
                            } else {
                                selectedPlayers.insert(player)
                            }
                        }
                    }

                    Button(action: {
                        createGameUseCase.invoke(players: players) { result, _ in
                            if result?.isSuccess() ?? false {
                                currentGame = result?.getOrNull()
                            }
                        }
                        
                        showNewGameSheet.toggle()
                    }) {
                        Text("Démarrer la partie")
                            .frame(maxWidth: .infinity)
                    }
                    .buttonStyle(.borderedProminent)
                    .controlSize(.large)
                    .disabled(selectedPlayers.isEmpty)
                    .padding([.horizontal, .bottom])
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
