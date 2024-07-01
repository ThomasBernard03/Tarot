package fr.thomasbernard03.tarot

import fr.thomasbernard03.tarot.commons.calculatePartnerScore
import junit.framework.TestCase
import org.junit.Test

class CalculatePartnerScoreUnitTest {

    @Test
    fun calculatePartnerScoreSmallOne(){
        val takerScore = -80
        val partnerScore = calculatePartnerScore(takerScore)

        TestCase.assertEquals(partnerScore, -40)
    }
    @Test
    fun calculatePartnerScoreGuardOne(){
        val takerScore = 200
        val partnerScore = calculatePartnerScore(takerScore)

        TestCase.assertEquals(partnerScore, 100)
    }
}