//
//  GameResultView.swift
//  iosApp
//
//  Created by Thomas Bernard on 22/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct GameResultView: View {
    
    let scores : [KotlinPair<PlayerModel, KotlinInt>]
    
    var body: some View {
        List {
            ForEach(scores.sorted { Int(truncating: $0.second!) > Int(truncating: $1.second!) }, id: \.first!.id) { playerScore in
                
                let index = scores.sorted { Int(truncating: $0.second!) > Int(truncating: $1.second!) }.firstIndex { $0.first!.id == playerScore.first!.id }!
                
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

                    
                    Text(String(Int(truncating: playerScore.second!)))
                        .foregroundColor(Int(truncating: playerScore.second!) >= 0 ? Color.green : Color.red)
                        .frame(width:50)
                }
            }
        }
    }
}
