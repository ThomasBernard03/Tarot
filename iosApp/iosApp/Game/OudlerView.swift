//
//  OudlerView.swift
//  iosApp
//
//  Created by Thomas Bernard on 20/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Shared

struct OudlerView: View {
    
    let oudler : Oudler
    
    var body: some View {
        Text(oudler.toLabel())
    }
}

#Preview {
    OudlerView(oudler: Oudler.excuse)
}
