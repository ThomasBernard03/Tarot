//
//  NewGameSheet.swift
//  iosApp
//
//  Created by Thomas Bernard on 17/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct NewGameSheet: View {
    
    @Binding var players : [PlayerModel]
    @Binding var selectedPlayers : Set<PlayerModel>
    var onCreateGame : () -> Void
    
    var body: some View {
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
                    } 
                    else {
                        selectedPlayers.insert(player)
                    }
                }
            }

            Button(action: { onCreateGame() }) {
                Text("Démarrer la partie")
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.borderedProminent)
            .controlSize(.large)
            .disabled(selectedPlayers.isEmpty)
            .padding([.horizontal, .bottom])
        }
                   
    }
}
