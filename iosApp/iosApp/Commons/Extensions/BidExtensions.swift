//
//  BidExtensions.swift
//  iosApp
//
//  Created by Thomas Bernard on 18/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

extension Bid {
    
    static func all() -> [Bid] {
        return [Bid.small, Bid.guard, Bid.guardWithout, Bid.guardAgainst]
    }
    
    
    func toLabel() -> String {
        if self == Bid.small {
            return "Petite"
        }
        else if self == Bid.guard {
            return "Garde"
        }
        else if self == Bid.guardWithout {
            return "Garde sans"
        }
        else if self == Bid.guardAgainst {
            return "Garde contre"
        }
        
        return ""
    }
}
