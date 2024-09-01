//
//  PlayerView.swift
//  iosApp
//
//  Created by Thomas Bernard on 15/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct PlayerView: View {
    
    let player: PlayerModel
    
    @State private var viewModel = ViewModel()
    @State private var playerName = ""
    @State private var playerColor : PlayerColor? = nil
    
    var body: some View {
        
        Form {
            Section("Apparance"){
                TextField("Nom du joueur", text: $playerName)
                
                HStack {
                    ScrollView(.horizontal) {
                        HStack {
                            ForEach(PlayerColor.all(), id: \.self) { color in
                                Circle()
                                    .foregroundColor(color.toColor())
                                    .frame(width: 50, height: 50)
                                    .overlay(
                                        Circle().stroke(Color.primary, lineWidth: playerColor == color  ? 4 : 0)
                                    )
                                    .onTapGesture {
                                        if playerColor == color {
                                            playerColor = nil
                                        }
                                        else {
                                            playerColor = color
                                        }
                                    }
                            }
                        }
                        .padding()
                    }
                }
                
                Button(action: {
//                    viewModel.editPlayer(id: player.id,name: playerName, color: playerColor)
                    viewModel.editPlayer(id: Int(player.id), name: playerName, color: playerColor!)
                }){
                    Text("Modifier le joueur")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
                .disabled(playerName.isEmpty || playerName == player.name || playerColor == nil)
                
            }
            
            
            Section("Statistiques"){
                Text("Module à venir...")
            }
            
        
        }
        .navigationTitle(player.name)
        .toolbar {
            
            Button(action: { }) {
                Label("Supprimer", systemImage: "trash")
                    .foregroundColor(player.color.toColor())
            }
        }
        .onAppear {
            playerName = player.name
            playerColor = player.color
        }
    }
}
