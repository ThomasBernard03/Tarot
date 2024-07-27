//
//  RoundDetailView.swift
//  iosApp
//
//  Created by Thomas Bernard on 24/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct RoundDetailView: View {
    
    let index : Int
    let round : RoundModel
    
    var body: some View {
        Form {
            
            Section("General"){
                HStack {
                    Text("Preneur")
                    Spacer()
                    Text(round.taker.name)
                }
                HStack {
                    Text("Contrat")
                    Spacer()
                    Text(round.bid.toLabel())
                }
            }
            
            Section("Bouts"){
                List {
                    ForEach(Oudler.all(), id: \.self){ oudler in
                        HStack {
                            oudler.toImage()
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(height:60)
                            
                            Text(oudler.toName())
                            
                            Spacer()
                            
                            if round.oudlers.contains(oudler){
                                Image(systemName: "checkmark")
                                    .foregroundColor(.accentColor)
                            }
                        }
                    }
                }
            }
            
            Section("Points"){
                Group {
                    VStack(alignment:.center) {
                        HStack {
                            Spacer()
                            Text(String(Int(round.points)))
                                .font(.system(size: 52))
                            Spacer()
                        }
                        
                        
//                        let requiredPoints : Int = Array(round.oulders).getRequiredPoints()
//                        let attackerScore : Int = Int(round.points) - requiredPoints
//                        
//                        Text(String(attackerScore))
//                            .font(.title3)
//                            .foregroundColor(attackerScore >= 0 ? Color.green : Color.red)
                    }
                }
            }
        }
        .navigationTitle("Tour n°" + String(index))
    }
}
