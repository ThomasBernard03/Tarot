package fr.thomasbernard03.tarot

import fr.thomasbernard03.tarot.commons.calculatePartnerScore
import fr.thomasbernard03.tarot.commons.calculateTakerScore
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import junit.framework.TestCase
import org.junit.Test

class CalculateTakerCalledHimSelfUnitTest {
    @Test
    fun calculateTakerCalledHimSelfOne(){
        val points = 30
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf()

        val takerScore = calculateTakerScore(points, bid, oudlers.size)
        val partnerScore = calculatePartnerScore(takerScore)
        TestCase.assertEquals(-204, takerScore + partnerScore)
    }
}