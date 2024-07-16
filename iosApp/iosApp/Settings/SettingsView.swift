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
                
                Section("Préférences"){
                    HStack {
                        Image(systemName: "sun.max").foregroundColor(.primary)
                        Toggle(isOn: $isAlwaysOnDisplay) {
                            Text("Laisser l'appareil allumé")
                        }
                        
                    }
                    Label(
                        title: { Text("Appareance") },
                        icon: { Image(systemName: "moon").foregroundColor(.primary) }
                    )
                    Label(
                        title: { Text("Langue") },
                        icon: { Image(systemName: "globe") }
                    )
                }
                
                Section("Autres"){
                    Label(
                        title: { Text("Tarot iOS 0.0.0 (1)") },
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
