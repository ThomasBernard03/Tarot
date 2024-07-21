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
    private let getGameHistoryUseCase = GetGameHistoryUseCase()
    private let deleteGameUseCase = DeleteGameUseCase()
    
    @State private var games: [GameModel] = []
    
    var body: some View {
        NavigationView {
            VStack(alignment:.center) {
                if games.isEmpty {
                    Text("Aucune partie réalisée")
                }
                else {
                    List {
                        ForEach(games, id: \.id){ game in
                            NavigationLink(destination: { EmptyView() }) {
                                HStack(spacing: 2) {
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
                        }
                        .onDelete { index in
                            let game = games[index.first!]
                            
                            deleteGameUseCase.invoke(id: game.id) { result, _ in
                                if result?.isSuccess() ?? false {
                                    games.remove(atOffsets: index)
                                }
                            }
                        }
                    }
                }
            }
            .navigationTitle("Historique")
            .toolbar {
                EditButton()
            }
            .onAppear {
                getGameHistoryUseCase.invoke { result, _ in
                    if result?.isSuccess() ?? false {
                        games = result?.getOrNull() as! [GameModel]
                    }
                }
            }
        }
    }
}

#Preview {
    HistoryView()
}
