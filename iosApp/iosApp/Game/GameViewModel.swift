//
//  GameViewModel.swift
//  iosApp
//
//  Created by Thomas Bernard on 23/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import Toast
import UIKit

extension GameView {
    @Observable
    class ViewModel {
        private let getCurrentGameUseCase = GetCurrentGameUseCase()
        private let getPlayersUseCase = GetPlayersUseCase()
        private let finishGameUseCase = FinishGameUseCase()
        private let createGameUseCase = CreateGameUseCase()
        private let createRoundUseCase = CreateRoundUseCase()
        private let deleteRoundUseCase = DeleteRoundUseCase()
        
        var currentGame : GameModel? = nil
        var players : [PlayerModel] = []
        
        
        func getCurrentGame(){
            Task {
                let result = try! await getCurrentGameUseCase.invoke()
                DispatchQueue.main.async {
                    self.currentGame = result.getOrNull()
                }
            }
        }
        
        func getPlayers(){
            Task {
                let result = try! await getPlayersUseCase.invoke()
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.players = (result.getOrNull() ?? []) as! [PlayerModel]
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible de récupérer les joueurs"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        
        func createGame(players : [PlayerModel]){
            Task {
                let result = try! await createGameUseCase.invoke(players: players)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.currentGame = result.getOrNull()
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible de créer la partie"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        func finishGame(){
            Task {
                let result = try! await finishGameUseCase.invoke(gameId: currentGame!.id)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.currentGame = nil
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible de terminer la partie en cours"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        func createRound(taker : PlayerModel, bid : Bid, calledPlayer : PlayerModel?, oudlers : [Oudler], points : Int){
            Task {
                let result = try! await createRoundUseCase.invoke(gameId: currentGame!.id, taker: taker, playerCalled: calledPlayer, bid: bid, oudlers: oudlers, points: Int32(points))
                
                if result.isSuccess() {
                    // TODO Add round into current game
                    getCurrentGame()
                }
                else {
                    let toast = Toast.default(
                      image: UIImage(systemName: "xmark.circle")!,
                      title: "Impossible de créer le tour"
                    )
                    toast.show()
                }
            }
        }
        
        func deleteRound(round : RoundModel){
            Task {
                let result = try! await deleteRoundUseCase.invoke(roundId: round.id)
                if result.isSuccess() {
                    getCurrentGame()
                }
                else {
                    let toast = Toast.default(
                      image: UIImage(systemName: "xmark.circle")!,
                      title: "Impossible de supprimer le tour"
                    )
                    toast.show()
                }
            }
        }
    }
}
