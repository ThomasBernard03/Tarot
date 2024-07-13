package commons.di

import data.repositories.GameRepositoryImpl
import data.repositories.PlayerRepositoryImpl
import data.repositories.RoundRepositoryImpl
import domain.repositories.GameRepository
import domain.repositories.PlayerRepository
import domain.repositories.RoundRepository
import domain.usecases.game.CreateGameUseCase
import domain.usecases.game.DeleteGameUseCase
import domain.usecases.game.FinishGameUseCase
import domain.usecases.game.GetCurrentGameUseCase
import domain.usecases.game.GetGameHistoryUseCase
import domain.usecases.game.GetGameUseCase
import domain.usecases.game.ResumeGameUseCase
import domain.usecases.player.CreatePlayerUseCase
import domain.usecases.player.DeletePlayerUseCase
import domain.usecases.player.EditPlayerUseCase
import domain.usecases.player.GetPlayerUseCase
import domain.usecases.player.GetPlayersUseCase
import domain.usecases.round.CreateRoundUseCase
import domain.usecases.round.DeleteRoundUseCase
import domain.usecases.round.EditRoundUseCase
import domain.usecases.round.GetRoundUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null){
    startKoin {
        config?.invoke(this)
        modules(
            provideRepositories,
            provideUseCases,
            platformModule(),
        )
    }
}

val provideRepositories = module {
    singleOf(::GameRepositoryImpl).bind(GameRepository::class)
    singleOf(::PlayerRepositoryImpl).bind(PlayerRepository::class)
    singleOf(::RoundRepositoryImpl).bind(RoundRepository::class)
}

val provideUseCases = module {
    singleOf(::CreateGameUseCase)
    singleOf(::DeleteGameUseCase)
    singleOf(::FinishGameUseCase)
    singleOf(::GetCurrentGameUseCase)
    singleOf(::GetGameHistoryUseCase)
    singleOf(::GetGameUseCase)
    singleOf(::ResumeGameUseCase)

    singleOf(::CreatePlayerUseCase)
    singleOf(::DeletePlayerUseCase)
    singleOf(::EditPlayerUseCase)
    singleOf(::GetPlayersUseCase)
    singleOf(::GetPlayerUseCase)

    singleOf(::CreateRoundUseCase)
    singleOf(::DeleteRoundUseCase)
    singleOf(::EditRoundUseCase)
    singleOf(::GetRoundUseCase)
}