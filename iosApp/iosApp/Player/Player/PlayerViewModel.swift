//
//  PlayerViewModel.swift
//  iosApp
//
//  Created by Thomas Bernard on 04/08/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

extension PlayerView {
    @Observable
    class ViewModel {
        private let deletePlayerUseCase = DeletePlayerUseCase()
        private let editPlayerUseCase = EditPlayerUseCase()
        
        func editPlayer(id : Int, name : String, color : PlayerColor){
            Task {
                let result = try! await editPlayerUseCase.invoke(id: Int64(id), name: name, color: color)
                DispatchQueue.main.async {
                    if result.isSuccess() {
                    }
                    else {
                        
                    }
                }
            }
        }
    }
}
