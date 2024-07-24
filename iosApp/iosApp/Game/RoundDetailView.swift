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
            
        }
        .navigationTitle("Tour n°" + String(index))
    }
}
