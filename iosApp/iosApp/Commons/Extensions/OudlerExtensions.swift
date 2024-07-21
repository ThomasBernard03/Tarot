//
//  OudlerExtensions.swift
//  iosApp
//
//  Created by Thomas Bernard on 18/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

extension Oudler {
    
    static func all() -> [Oudler] {
        return [Oudler.petit, Oudler.excuse, Oudler.grand]
    }
    
    
    func toLabel() -> String {
        if self == Oudler.petit {
            return "1"
        }
        if self == Oudler.excuse {
            return "*"
        }
        if self == Oudler.grand {
            return "21"
        }
        
        return ""
    }

}



extension Array where Element == Oudler {
    func getRequiredPoints() -> Int {
        switch self.count {
        case 0:
            return 56
        case 1:
            return 51
        case 2:
            return 41
        case 3:
            return 36
        default:
            return 0
        }
    }
}
