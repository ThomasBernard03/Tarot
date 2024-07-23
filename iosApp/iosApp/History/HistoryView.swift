//
//  HistoryView.swift
//  iosApp
//
//  Created by Thomas Bernard on 16/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct HistoryView: View {
    
    @State private var viewModel = ViewModel()    
    
    var body: some View {
        NavigationStack {
            VStack(alignment:.center) {
                if viewModel.games.isEmpty {
                    Text("Aucune partie réalisée")
                }
                else {
                    List {
                        ForEach(viewModel.games, id: \.id){ game in
                            NavigationLink(destination: { HistoryGameView(game: game) }) {
                                HStack(spacing: 2) {
                                    ZStack {
                                        Color.accentColor
                                        VStack {
                                            Text(String(game.startedAt.date.dayOfMonth))
                                            Text(String(game.startedAt.month.name.lowercased()))
                                        }
                                        
                                    }
                                    .frame(width:100)
                                    
                                    ForEach(game.players, id : \.id){ player in
                                        ZStack {
                                            Circle()
                                                .foregroundColor(player.color.toColor())
                                                .frame(width: 25, height: 25)
                                            Text("" + player.name.uppercased().prefix(1))
                                                .foregroundColor(.white)
                                                .font(.system(size: 12))
                                        }
                                    }
                                    
                                    Spacer()
                                    
                                    // Text(date.timeIntervalSinceNow)
                                    
                                }
                            }
                            .swipeActions(edge:.leading){
                                Button(action: { viewModel.resumeGame(game: game) }){
                                    Label("Reprendre la partie", systemImage: "play.circle")
                                }
                                .tint(.indigo)
                            }
                        }
                        .onDelete { index in
                            let game = viewModel.games[index.first!]
                            viewModel.deleteGame(game: game)
                        }
                    }
                }
            }
            .navigationTitle("Historique")
            .toolbar {
                EditButton()
            }
            .onAppear { viewModel.getGameHistory() }
        }
    }
}

#Preview {
    HistoryView()
}
