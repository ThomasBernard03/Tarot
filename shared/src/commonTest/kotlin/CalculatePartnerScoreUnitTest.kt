import commons.calculatePartnerScore
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatePartnerScoreUnitTest {

    @Test
    fun calculatePartnerScoreSmallOne(){
        val takerScore = -80
        val partnerScore = calculatePartnerScore(takerScore)

        assertEquals(partnerScore, -40)
    }

    @Test
    fun calculatePartnerScoreGuardOne(){
        val takerScore = 200
        val partnerScore = calculatePartnerScore(takerScore)

        assertEquals(partnerScore, 100)
    }
}