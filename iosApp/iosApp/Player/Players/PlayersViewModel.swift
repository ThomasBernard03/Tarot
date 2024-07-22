//
//  PlayersViewModel.swift
//  iosApp
//
//  Created by Thomas Bernard on 22/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import Toast
import UIKit

extension PlayersView {
    @Observable
    class ViewModel {
        private let getPlayersUseCase = GetPlayersUseCase()
        private let deletePlayerUseCase = DeletePlayerUseCase()
        private let createPlayerUseCase = CreatePlayerUseCase()
        
        private(set) var players = [PlayerModel]()
        
        func getPlayers(){
            Task {
                let result = try! await self.getPlayersUseCase.invoke()
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.players = (result.getOrNull() ?? []) as! [PlayerModel]
                    }
                    else {
                        let toast = Toast.default(
                            image: UIImage(systemName: "xmark.circle")!,
                            title: "Erreur lors de la récupération des joueurs"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        func deletePlayer(player : PlayerModel){
            Task {
                let result = try! await deletePlayerUseCase.invoke(id: player.id)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        let index = self.players.firstIndex(of: player)!
                        self.players.remove(at: index)
                    }
                    else {
                        let toast = Toast.default(
                            image: UIImage(systemName: "xmark.circle")!,
                            title: "Impossible de supprimer le joueur",
                            subtitle: "Il a participé à des parties"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        func createPlayer(name : String, color : PlayerColor){
            Task {
                let player = CreatePlayerModel(name: name, color: color)
                let result = try! await createPlayerUseCase.invoke(player: player)
                
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.players.append((result.getOrNull()!))
                    }
                    else {
                        let toast = Toast.default(
                            image: UIImage(systemName: "xmark.circle")!,
                            title: "Impossible de créer le joueur"
                        )
                        toast.show()
                    }
                }
            }
        }
    }
}
