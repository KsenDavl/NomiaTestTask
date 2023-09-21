package com.example.nomiatesttask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс ActionCounter должен ")
class ActionCounterTest {

    private ActionCounter actionCounter;

    @BeforeEach
    void init() {
        actionCounter = new ActionCounter();
    }

    @Test
    @DisplayName("вернуть 7")
    void shouldReturnSeven() {

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
    @DisplayName("вернуть один, когда только один вызов был сделан в последние 5 минут")
    void shouldReturnOneWhenOnlyLastCallInLastFiveMinutes() {

        actionCounter.call(1695198798);
        actionCounter.call(1695198888);
        actionCounter.call(1695198889);
        actionCounter.call(1695198899);

        actionCounter.call(1695301072);


        int result = actionCounter.getActions(1695301162);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("вернуть четыре, когда все вызовы кроме первого были сделаны в последние 5 минут")
    void shouldReturnFourWhenOnlyFirstCallNotInLastFiveMinutes() {

        actionCounter.call(1695198798);

        actionCounter.call(1695301060);
        actionCounter.call(1695301062);
        actionCounter.call(1695301112);
        actionCounter.call(1695301160);


        int result = actionCounter.getActions(1695301162);

        assertEquals(4, result);
    }

    @Test
    @DisplayName("вернуть ноль, когда нет вызовов за последние 5 минут")
    void shouldReturnZeroWhenNoCallsInLastFiveMinutes() {

        actionCounter.call(1695198798);
        actionCounter.call(1695198888);
        actionCounter.call(1695198889);
        actionCounter.call(1695198899);

        int result = actionCounter.getActions(1695300862);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("вернуть 4, когда все вызовы сделаны в последние 5 минут")
    void shouldReturnFourWhenAllCallsInLastFiveMinutes() {

        actionCounter.call(1695300762);
        actionCounter.call(1695300852);
        actionCounter.call(1695300860);
        actionCounter.call(1695300862);

        int result = actionCounter.getActions(1695300862);

        assertEquals(4, result);
    }

}