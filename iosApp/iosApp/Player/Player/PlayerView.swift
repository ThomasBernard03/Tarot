//
//  PlayerView.swift
//  iosApp
//
//  Created by Thomas Bernard on 15/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PlayerView: View {
    
    let id : Int64
    let name : String
    
    var body: some View {
        Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
            .navigationTitle(name)
            .toolbar {
                Button("Modifier"){
                    
                }
            }
    }
}

#Preview {
    PlayerView(id: 1, name: "Thomas")
}
