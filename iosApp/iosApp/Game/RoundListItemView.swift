//
//  RoundListItemView.swift
//  iosApp
//
//  Created by Thomas Bernard on 21/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct RoundListItemView: View {
    
    let round : RoundModel
    let numberOfPlayers : Int

    var body: some View {
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
                let takerScore = round.calculateTakerScore(numberOfPlayers: Int32(numberOfPlayers))
                
                Text(String(takerScore))
                    .foregroundColor(takerScore >= 0 ? Color.green : Color.red)
                    .padding(.trailing, 5)
                    .fontWeight(.bold)
                
                if (round.calledPlayer != nil && round.calledPlayer != round.taker){
                    
                    let calledPlayerScore = round.calculatePartnerScore()
                    
                    Text(String(calledPlayerScore))
                        .foregroundColor(calledPlayerScore >= 0 ? Color.green : Color.red)
                        .padding(.trailing, 5)
                        .font(.system(size: 12))
                }
            }
        }
    }
}
