//
//  NewGameSheet.swift
//  iosApp
//
//  Created by Thomas Bernard on 17/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared
// import MultiPicker

struct NewGameSheet: View {
    
    @Binding var players : [PlayerModel]
    var onCreateGame : ([PlayerModel]) -> Void
    
    @State private var selectedPlayers : Set<PlayerModel> = []
    
    var body: some View {
        NavigationStack {
            Form {
                Section("Séléctionnez entre 3 et 5 joueurs"){
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
                            }
                            else {
                                selectedPlayers.insert(player)
                            }
                        }
                    }
                }
                
                Button(action: { onCreateGame(Array(selectedPlayers)) }) {
                    Text("Démarrer la partie")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
                .disabled(selectedPlayers.count > 5 || selectedPlayers.count < 3)
                
            }
            .navigationTitle("Nouvelle partie")
        }
    }
}
