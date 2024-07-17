import commons.calculateDefenderScore
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateDefenderScoreUnitTest {
    @Test
    fun calculateDefendersScoreOne(){
        val takerScore = -80
        val mateScore = -40
        val defendersScore = calculateDefenderScore(takerScore + mateScore, numberOfDefenders = 3)

        assertEquals(defendersScore, 40)
    }

    @Test
    fun calculateDefendersScoreTwo(){
        val takerScore = -160
        val defendersScore = calculateDefenderScore(takerScore, numberOfDefenders = 4)

        assertEquals(defendersScore, 40)
    }

    @Test
    fun calculateDefendersScoreThree(){
        val takerScore = -224
        val defendersScore = calculateDefenderScore(takerScore, numberOfDefenders = 4)

        assertEquals(56, defendersScore)
    }
}