package com.example.nomiatesttask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Класс ATM должен ")
class ATMTest {

    private ATM atm;

    @BeforeEach
    void init() {
        atm = new ATM();
    }

    @Test
    @DisplayName("корректно вносить и хранить банкноты")
    void shouldDepositAllBanknotesAndStoreThemCorrectly() {
        atm.deposit(new int[]{1, 2, 3, 4, 5});

        int expectedMoneyInAtm = 10 + 2 * 50 + 3 * 100 + 4 * 200 + 5 * 500;
        assertEquals(expectedMoneyInAtm, atm.getMoneyCounter().count());
    }

    @Test
    @DisplayName("корректно вносить банкноты, даже если никакие банкноты не вносятся")
    void shouldDepositCorrectlyWhenInputsEmpty() {
        atm.deposit(new int[]{0, 0, 0, 0, 0});

        int expectedMoneyInAtm = 0;
        assertEquals(expectedMoneyInAtm, atm.getMoneyCounter().count());
    }

    @Test
    @DisplayName("возвращать -1, когда запрашивается большее количество денег, чем есть в банкомате")
    void shouldReturnMinus1WhenAmountToWithdrawBiggerThanAmountInATM() {
        atm.deposit(new int[]{0, 0, 0, 0, 1});
        int[] result = atm.withdraw(1000);

        assertEquals(-1, result[0]);
    }

    @Test
    @DisplayName("возвращать -1, когда невозможно выдать запрашиваемую сумму")
    void shouldReturnMinus1WhenImpossibleToWithdraw() {
        atm.deposit(new int[]{0, 0, 0, 3, 1});
        int[] result = atm.withdraw(600);

        assertEquals(-1, result[0]);
    }

    @Test
    @DisplayName("вернуть 1х100 и 1х200 банкноты при запрашиваемой сумме 300")
    void shouldReturnAHundredAndATwoHundredBanknotesWhenThreeHundredNeeded() {
        atm.deposit(new int[]{0, 2, 1, 1, 0});
        int[] result = atm.withdraw(300);

        assertArrayEquals(new int[]{0, 0, 1, 1, 0}, result);
    }

    @Test
    @DisplayName("должен корректно отработать ряд запросов из условия задачи")
    void shouldCorrectlyWorkTheTaskAlgorithm() {
        atm.deposit(new int[]{0, 0, 1, 2, 1});
        assertArrayEquals(new int[]{0, 0, 1, 0, 1}, atm.withdraw(600));
        atm.deposit(new int[]{0, 1, 0, 1, 1});
        assertArrayEquals(new int[]{-1}, atm.withdraw(600));
        assertArrayEquals(new int[]{0, 1, 0, 0, 1}, atm.withdraw(550));
        assertEquals(600, atm.getMoneyCounter().count());
    }

    @Test
    @DisplayName("выбросить исключение, если при внесении указаны отрицательные числа")
    void shouldThrowExceptionWhenArrayContainsNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(new int[]{0, -10, 1, 2, 1}));
    }

    @Test
    @DisplayName("выбросить исключение, если длина массива при внесении не равна пяти")
    void shouldThrowExceptionWhenArrayLengthIsNotFive() {
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(new int[]{0, 10, 1}));
        assertThrows(IllegalArgumentException.class, () -> atm.deposit(new int[]{0, 10, 1, 6, 2, 1}));
    }

    @Test
    @DisplayName("выбросить исключение, если сумма для снятия равна 0, или отрицательна, или больше 10^9")
    void shouldThrowExceptionWhenAmountToWithdrawIsNegativeOrZeroOrTooBig() {
        atm.deposit(new int[]{0, 1, 0, 1, 1});
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(-300));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(2000000000));
    }
}