package fr.thomasbernard03.tarot

import fr.thomasbernard03.tarot.commons.calculateDefenderScore
import junit.framework.TestCase
import org.junit.Test

class CalculateDefenderScoreUnitTest {
    @Test
    fun calculateDefendersScoreOne(){
        val takerScore = -80
        val mateScore = -40
        val defendersScore = calculateDefenderScore(takerScore + mateScore, numberOfDefenders = 3)

        TestCase.assertEquals(defendersScore, 40)
    }

    @Test
    fun calculateDefendersScoreTwo(){
        val takerScore = -160
        val defendersScore = calculateDefenderScore(takerScore, numberOfDefenders = 4)

        TestCase.assertEquals(defendersScore, 40)
    }
}