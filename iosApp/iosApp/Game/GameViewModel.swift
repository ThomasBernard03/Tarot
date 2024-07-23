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
        
        
        
        var currentGame : GameModel? = nil
        
        
        func getCurrentGame(){
            Task {
                let result = try! await getCurrentGameUseCase.invoke()
                DispatchQueue.main.async {
                    if result.isSuccess() {
                        self.currentGame = result.getOrNull()
                    }
                    else {
                        let toast = Toast.default(
                          image: UIImage(systemName: "xmark.circle")!,
                          title: "Impossible de récupérer la partie en cours"
                        )
                        toast.show()
                    }
                }
            }
        }
    }
}
