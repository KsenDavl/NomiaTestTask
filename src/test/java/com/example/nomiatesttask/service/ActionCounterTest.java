package com.example.nomiatesttask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionCounterTest {

    private ActionCounter actionCounter;

    @BeforeEach
    void init() {
        actionCounter = new ActionCounter();
    }

    @Test
    void shouldReturnSevenCallsOutOfTen() {

        actionCounter.call(1695298798);
        actionCounter.call(1695298888);
        actionCounter.call(1695298889);

        actionCounter.call(1695298899);
        actionCounter.call(1695298900);
        actionCounter.call(1695298910);
        actionCounter.call(1695298920);
        actionCounter.call(1695298930);
        actionCounter.call(1695298940);
        actionCounter.call(1695299000);

        int result = actionCounter.getActions(1695299198);

        assertEquals(7, result);
    }

    @Test
    void shouldReturnOneWhenOnlyLastCallInFiveMinutes() {

        actionCounter.call(1695198798);
        actionCounter.call(1695198888);
        actionCounter.call(1695198889);
        actionCounter.call(1695198899);

        actionCounter.call(1695301072);


        int result = actionCounter.getActions(1695301162);

        assertEquals(1, result);
    }
    @Test
    void shouldReturnOneWhenOnlyFirstCallNotInFiveMinutes() {

        actionCounter.call(1695198798);

        actionCounter.call(1695301060);
        actionCounter.call(1695301062);
        actionCounter.call(1695301112);
        actionCounter.call(1695301160);


        int result = actionCounter.getActions(1695301162);

        assertEquals(4, result);
    }


    @Test
    void shouldReturnZeroWhenNoCallsInFiveMinutes() {

        actionCounter.call(1695198798);
        actionCounter.call(1695198888);
        actionCounter.call(1695198889);
        actionCounter.call(1695198899);

        int result = actionCounter.getActions(1695300862);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnFourWhenAllCallsInFiveMinutes() {

        actionCounter.call(1695300762);
        actionCounter.call(1695300852);
        actionCounter.call(1695300860);
        actionCounter.call(1695300862);

        int result = actionCounter.getActions(1695300862);

        assertEquals(4, result);
    }

}