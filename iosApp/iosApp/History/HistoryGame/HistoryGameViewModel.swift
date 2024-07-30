//
//  HistoryGameViewModel.swift
//  iosApp
//
//  Created by Thomas Bernard on 22/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import Toast
import UIKit

extension HistoryGameView {
    @Observable
    class ViewModel {
        
        private let resumeGameUseCase = ResumeGameUseCase()
        private let deleteGameUseCase = DeleteGameUseCase()
        
        func resumeGame(game : GameModel){
            Task {
                let result = try! await self.resumeGameUseCase.invoke(id: game.id)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        // TODO Navigate to current game screen
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
        
        func deleteGame(game : GameModel){
            Task {
                let result = try! await self.deleteGameUseCase.invoke(id: game.id)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        // TODO Go back
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible de supprimer la partie"
                        )
                        toast.show()
                    }
                }
            }
        }
    }
}
