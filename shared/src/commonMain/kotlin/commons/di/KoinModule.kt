package commons.di

import data.local.ApplicationDatabase
import data.local.dao.GameDao
import data.local.dao.PlayerDao
import data.local.dao.PlayerGameDao
import data.local.dao.RoundDao
import domain.repositories.GameRepository
import domain.repositories.PlayerRepository
import domain.repositories.RoundRepository
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import data.repositories.GameRepositoryImpl
import org.koin.dsl.bind
import data.repositories.PlayerRepositoryImpl
import data.repositories.RoundRepositoryImpl
import domain.usecases.game.*
import domain.usecases.player.*
import domain.usecases.round.*
import org.koin.core.module.Module

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