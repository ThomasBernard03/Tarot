import commons.calculateTakerScore
import domain.models.Bid
import domain.models.Oudler
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateTakerCalledHimSelfUnitTest {
    @Test
    fun calculateTakerCalledHimSelfOne(){
        val points = 30
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf()

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        assertEquals(-204, takerScore)
    }

    @Test
    fun calculateTakerCalledHimSelfTwo(){
        val points = 36
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf(Oudler.EXCUSE)

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        assertEquals(-160, takerScore)
    }


    @Test
    fun negativeScoreSmallZeroOudler(){
        val points = 23
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf()

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        assertEquals(-232, takerScore)
    }

    @Test
    fun negativeScoreSmallOneOudler(){
        val points = 27
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf(Oudler.PETIT)

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        assertEquals(-196, takerScore)
    }

    @Test
    fun negativeScoreGuardTwoOudler(){
        val points = 38
        val bid = Bid.GUARD
        val oudlers : List<Oudler> = listOf(Oudler.PETIT, Oudler.EXCUSE)

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        assertEquals(-224, takerScore)
    }
}