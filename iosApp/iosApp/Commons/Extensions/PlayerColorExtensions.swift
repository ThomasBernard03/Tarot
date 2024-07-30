//
//  PlayerColorExtensions.swift
//  iosApp
//
//  Created by Thomas Bernard on 15/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import SwiftUI

extension PlayerColor {
    func toColor() -> Color {
        switch self {
        case .red:
            return Color.red
        case .blue:
            return Color.blue
        case .green:
            return Color.green
        case .orange:
            return Color.orange
        case .purple:
            return Color.purple
        case .yellow:
            return Color.yellow
        case .pink:
            return Color.pink
        case .brown:
            return Color.brown
        default:
            return Color.black
        }
    }
    
    static func all() -> [PlayerColor] {
        return [.red, .blue, .green, .orange, .purple, .yellow, .pink, .brown]
    }
}
