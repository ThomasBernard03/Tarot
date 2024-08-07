package fr.thomasbernard03.tarot.commons.di

import fr.thomasbernard03.tarot.commons.helpers.NavigationHelper
import fr.thomasbernard03.tarot.commons.helpers.ResourcesHelper
import fr.thomasbernard03.tarot.commons.helpers.implementations.NavigationHelperImpl
import fr.thomasbernard03.tarot.commons.helpers.implementations.ResourcesHelperImpl
import fr.thomasbernard03.tarot.presentation.game.GameViewModel
import fr.thomasbernard03.tarot.presentation.history.HistoryViewModel
import fr.thomasbernard03.tarot.presentation.player.player.PlayerViewModel
import fr.thomasbernard03.tarot.presentation.player.players.PlayersViewModel
import fr.thomasbernard03.tarot.presentation.round.RoundViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::GameViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::PlayerViewModel)
    viewModelOf(::PlayersViewModel)
    viewModelOf(::RoundViewModel)
}

val helpersModule = module {
    single<ResourcesHelper> { ResourcesHelperImpl() }
    single<NavigationHelper> { NavigationHelperImpl() }
}