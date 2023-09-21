package com.example.nomiatesttask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ATMTest {

    private static ATM atm;

    @BeforeEach
    void init() {
        atm = new ATM();
    }

    @Test
    void shouldDepositAllBanknotesAndStoreThemCorrectly() {
        atm.deposit(new int[]{1, 2, 3, 4, 5});

        int expectedMoneyInAtm = 10 + 2 * 50 + 3 * 100 + 4 * 200 + 5 * 500;
        assertEquals(expectedMoneyInAtm, atm.getMoneyCounter().count());
    }

    @Test
    void shouldDepositCorrectlyWhenInputsEmpty() {
        atm.deposit(new int[]{0, 0, 0, 0, 0});

        int expectedMoneyInAtm = 0;
        assertEquals(expectedMoneyInAtm, atm.getMoneyCounter().count());
    }

    @Test
    void shouldReturnMinus1WhenAmountToWithdrawBiggerThanAmountInATM() {
        atm.deposit(new int[]{0, 0, 0, 0, 1});
        int[] result = atm.withdraw(1000);

        assertEquals(-1, result[0]);
    }

    @Test
    void shouldReturnMinus1WhenImpossibleToWithdraw() {
        atm.deposit(new int[]{0, 0, 0, 3, 1});
        int[] result = atm.withdraw(600);

        assertEquals(-1, result[0]);
    }

    @Test
    void shouldReturnAHundredAnd2TwoHundredBanknotesWhenThreeHundredNeeded() {
        atm.deposit(new int[]{0, 2, 1, 1, 0});
        int[] result = atm.withdraw(300);

        assertArrayEquals(new int[]{0, 0, 1, 1, 0}, result);
    }
}