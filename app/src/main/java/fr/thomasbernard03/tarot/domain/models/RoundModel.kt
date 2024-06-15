package fr.thomasbernard03.tarot.domain.models

import java.util.Date

data class RoundModel(
    val id : Long,
    val finishedAt : Date,
    val takerModel: TakerModel
)