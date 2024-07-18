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
}
