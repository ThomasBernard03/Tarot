//
//  SettingsView.swift
//  iosApp
//
//  Created by Thomas Bernard on 16/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SettingsView: View {
    @State private var isAlwaysOnDisplay : Bool = false
    @Environment(\.openURL) var openURL
    
    private var appVersion: String {
         Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "Unknown"
     }
     
     private var appBuild: String {
         Bundle.main.object(forInfoDictionaryKey: "CFBundleVersion") as? String ?? "Unknown"
     }
    
    var body: some View {
        NavigationStack {
            Form {
                Section("Général"){
                    Button(action: {openURL(URL(string: "https://www.fftarot.fr/assets/documents/R-RO201206.pdf")!)}) {
                        Label(
                            title: { Text("Règles officielles").foregroundColor(.primary) },
                            icon: { Image(systemName: "book.closed") }
                        )
                    }
                    Button(action: {
                        openURL(URL(string: "https://github.com/ThomasBernard03/Tarot")!)
                    }) {
                        Label(
                            title: { Text("Code open source").foregroundColor(.primary) },
                            icon: { Image(systemName: "swift").foregroundColor(.orange) }
                        )
                    }
                    Button(action: {
                        openURL(URL(string: "https://github.com/ThomasBernard03/Tarot/issues/new")!)
                    }) {
                        Label(
                            title: { Text("Remonter un bug").foregroundColor(.primary) },
                            icon: { Image(systemName: "ladybug").foregroundColor(.green) }
                        )
                    }
                }
                
                Section("Autres"){
                    Label(
                        title: { Text("Tarot iOS \(appVersion) (\(appBuild))") },
                        icon: { Image(systemName: "apple.logo").foregroundColor(.primary) }
                    )
                }
            }
            .navigationTitle("Paramètres")
        }
    }
}

#Preview {
    SettingsView()
}
