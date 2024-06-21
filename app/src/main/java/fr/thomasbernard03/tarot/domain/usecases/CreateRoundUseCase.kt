package fr.thomasbernard03.tarot.domain.usecases

import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import fr.thomasbernard03.tarot.domain.models.PlayerModel
import fr.thomasbernard03.tarot.domain.models.Resource
import fr.thomasbernard03.tarot.domain.models.RoundModel
import fr.thomasbernard03.tarot.domain.models.errors.CreateRoundError
import fr.thomasbernard03.tarot.domain.repositories.RoundRepository
import org.koin.java.KoinJavaComponent.get

class CreateRoundUseCase(
    private val roundRepository: RoundRepository = get(RoundRepository::class.java)
) {
    suspend operator fun invoke(
        gameId : Long,
        taker : PlayerModel,
        playerCalled : PlayerModel?,
        bid : Bid,
        oudlers : List<Oudler>,
        points : Int
    ) : Resource<RoundModel, CreateRoundError> {
        return roundRepository.createRound(gameId, taker, playerCalled, bid, oudlers, points)
    }
}