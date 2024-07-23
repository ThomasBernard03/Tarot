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
    
    let player : PlayerModel
    
    var body: some View {
        Form {
            
        }
        .navigationTitle(player.name)
        .toolbar {
            Button(action : { }) {
                Label("Modifier", systemImage: "pencil.circle")
            }
            
            Button(action : { }) {
                Label("Supprimer", systemImage: "trash")
            }
        }
    }
}
