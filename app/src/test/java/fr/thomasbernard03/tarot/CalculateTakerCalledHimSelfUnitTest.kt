package fr.thomasbernard03.tarot

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

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        TestCase.assertEquals(-204, takerScore)
    }

    @Test
    fun calculateTakerCalledHimSelfTwo(){
        val points = 36
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf(Oudler.EXCUSE)

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        TestCase.assertEquals(-160, takerScore)
    }


    @Test
    fun negativeScoreSmallZeroOudler(){
        val points = 23
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf()

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        TestCase.assertEquals(-232, takerScore)
    }

    @Test
    fun negativeScoreSmallOneOudler(){
        val points = 27
        val bid = Bid.SMALL
        val oudlers : List<Oudler> = listOf(Oudler.PETIT)

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        TestCase.assertEquals(-196, takerScore)
    }

    @Test
    fun negativeScoreGuardTwoOudler(){
        val points = 38
        val bid = Bid.GUARD
        val oudlers : List<Oudler> = listOf(Oudler.PETIT, Oudler.EXCUSE)

        val takerScore = calculateTakerScore(points, bid, oudlers.size, calledHimSelf = true)
        TestCase.assertEquals(-224, takerScore)
    }
}