//
//  HistoryViewModel.swift
//  iosApp
//
//  Created by Thomas Bernard on 22/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import Toast
import UIKit

extension HistoryView {
    @Observable
    class ViewModel {
        private let getGameHistoryUseCase = GetGameHistoryUseCase()
        private let deleteGameUseCase = DeleteGameUseCase()
        private let resumeGameUseCase = ResumeGameUseCase()
        
        private(set) var games = [GameModel]()
        
        func getGameHistory(){
            Task {
                let result = try! await getGameHistoryUseCase.invoke()
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.games = result.getOrNull() as! [GameModel]
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible de récupérer l\'historique"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        func deleteGame(game : GameModel){
            Task {
                let result = try! await deleteGameUseCase.invoke(id: game.id)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.games.removeAll { $0.id == game.id }
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible supprimer la partie"
                        )
                        toast.show()
                    }
                }
            }
        }
        
        func resumeGame(game : GameModel){
            Task {
                let result = try! await resumeGameUseCase.invoke(id: game.id)
                if result.isSuccess() {
                    getGameHistory()
                }
                else {
                    let toast = Toast.default(
                      image: UIImage(systemName: "xmark.circle")!,
                      title: "Impossible de reprendre la partie"
                    )
                    toast.show()
                }
            }
        }
    }
}
