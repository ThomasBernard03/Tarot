package fr.thomasbernard03.tarot

import fr.thomasbernard03.tarot.commons.calculateTakerScore
import fr.thomasbernard03.tarot.domain.models.Bid
import fr.thomasbernard03.tarot.domain.models.Oudler
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FivePlayerGameUnitTest {
    @Test
    fun checkTakerPointSmallOne(){

        val points = 57
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf()
        val players = 5


        val score = calculateTakerScore(points, bid, oudlers.size, players, false)

        // Score must be 100
        assertEquals(52, score)
    }

    @Test
    fun checkTakerPointSmallTwo(){
        val points = 24
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf()
        val players = 5

        val score = calculateTakerScore(points, bid, oudlers.size, players, false)
        assertEquals(-114, score)
    }


    @Test
    fun checkTakerPointGuardOne(){
        val points = 61
        val bid = Bid.GUARD
        val oudlers : List<Oudler> = listOf(Oudler.EXCUSE)
        val players = 5

        val score = calculateTakerScore(points, bid, oudlers.size, players, false)
        assertEquals(140, score)
    }

    @Test
    fun checkTakerPointGuardWithoutOne(){
        val points = 63
        val bid = Bid.GUARD_WITHOUT
        val oudlers : List<Oudler> = listOf(Oudler.PETIT, Oudler.EXCUSE)
        val players = 5

        val score = calculateTakerScore(points, bid, oudlers.size, players, false)
        assertEquals(376, score)
    }

    @Test
    fun checkTakerPointGuardWithoutTwo(){
        val points = 71
        val bid = Bid.GUARD_WITHOUT
        val oudlers : List<Oudler> = listOf(Oudler.PETIT, Oudler.EXCUSE, Oudler.GRAND)
        val players = 5

        val score = calculateTakerScore(points, bid, oudlers.size, players, false)
        assertEquals(480, score)
    }
}