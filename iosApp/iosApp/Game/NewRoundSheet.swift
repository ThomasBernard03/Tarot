//
//  NewRoundSheet.swift
//  iosApp
//
//  Created by Thomas Bernard on 20/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct NewRoundSheet: View {
    var players : [PlayerModel]
    var onCreateRound : (PlayerModel, Bid, PlayerModel?, [Oudler], Int) -> Void
    
    @State private var taker: PlayerModel
    @State private var bid: Bid = Bid.small
    @State private var calledPlayer: PlayerModel
    @State private var numberOfOudlers: Int = 0
    @State private var points: Double = 0
    @State private var attackSide: Bool = true
    @State private var oudlers : [Oudler] = []
    
    init(players: [PlayerModel], onCreateRound: @escaping (_ taker : PlayerModel, _ bid : Bid, _ calledPlayer : PlayerModel?, _ oudlers : [Oudler], _ points : Int) -> Void) {
        self.players = players
        self.onCreateRound = onCreateRound
        self.taker = players.first!
        self.calledPlayer = players.first!
    }
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .leading, spacing: 12) {
                Form {
                    Section("General") {
                        Picker("Preneur",selection: $taker) {
                            ForEach(players, id: \.id) {
                                Text($0.name).tag($0)
                           }
                         }.pickerStyle(.menu)
                        
                        Picker("Contrat",selection: $bid) {
                            ForEach(Bid.all(), id: \.self) {
                                Text($0.toLabel()).tag($0)
                            }
                         }.pickerStyle(.menu)
       
                        
                        if(players.count >= 5){
                            Picker("Joueur appelé",selection: $calledPlayer) {
                                ForEach(players, id: \.id) {
                                    Text($0.name).tag($0)
                                }
                             }.pickerStyle(.menu)
                        }
                        
                        Picker("Bouts",selection: $numberOfOudlers) {
                            ForEach([0, 1, 2, 3], id: \.self) {
                                Text(String($0)).tag($0)
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
                                
                                Text(String(format: "%.0f", points))
                                    .font(.system(size: 52))
                                
                                let attackScore = Int(points) - Oudler.all().getRequiredPoints()
                                
                                Text(String(attackScore))
                                    .font(.title3)
                                    .foregroundColor(attackScore >= 0 ? Color.green : Color.red)
                                    
                                
                                HStack {
                                    Button(action: {
                                        if points > 0 {
                                            points -= 1
                                        }
                                            
                                    }) {
                                        Label("Moins", systemImage: "minus")
                                    }
                                    Slider(value: $points, in: 0...91, step: 1)
                                    Button(action: {
                                        if points < 91 {
                                            points += 1
                                        }
                                            
                                        
                                    }) {
                                        Label("Plus", systemImage: "plus")
                                    }
                                }
                                .buttonStyle(.borderless)
                                .labelStyle(.iconOnly)
                                
                                
                            }
                        }
                    }
                    
                    
                    
                    Section("Bonus"){
                        
                    }
                }
                
                

                

                Button(action: { onCreateRound(taker, bid, calledPlayer, oudlers, Int(points)) }) {
                    Text("Ajouter le tour")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                .controlSize(.large)
                .padding([.horizontal, .bottom])
            }
            .navigationTitle("Nouveau tour")
        }
    }
}

#Preview {
    let players : [PlayerModel] = [PlayerModel(id: 1, name: "Thomas", color: PlayerColor.red)]
    return NewRoundSheet(players: players) { taker, bid, calledPlayer, oudlers, points in
        
    }
}
