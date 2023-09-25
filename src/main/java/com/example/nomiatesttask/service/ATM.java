package com.example.nomiatesttask.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ATM {

    private Map<Integer, Integer> atmAvailableMoney;
    private final MoneyDepositor moneyDepositor;
    private final MoneyWithdrawer moneyWithdrawer;
    private final MoneyCounter moneyCounter;

    public ATM() {
        this.moneyCounter = new MoneyCounter();
        this.moneyDepositor = new MoneyDepositor();
        this.moneyWithdrawer = new MoneyWithdrawer();
        atmAvailableMoney = new HashMap<>();
    }

    public void deposit(int[] banknotesCount) {
        if (banknotesCount.length != 5) {
            throw new IllegalArgumentException("The input should be an array of 5 integers");
        }
        moneyDepositor.deposit(banknotesCount);
    }

    public int[] withdraw(int amount) {
        if (amount < 1 || amount > 1000000000) {
            throw new IllegalArgumentException("The amount to withdraw should be a positive integer less than 1000000000");
        }
        return moneyWithdrawer.withdraw(amount);
    }

    public MoneyCounter getMoneyCounter() {
        return moneyCounter;
    }

    private class MoneyDepositor {
        void deposit(int[] banknotesCount) {
            for (int i : banknotesCount) {
                if (i < 0 || i > 1000000000) {
                    throw new IllegalArgumentException("The amount of banknotes should be zero or a positive integer less than 1000000000");
                }
            }

            atmAvailableMoney.put(10, atmAvailableMoney.getOrDefault(10, 0) + banknotesCount[0]);
            atmAvailableMoney.put(50, atmAvailableMoney.getOrDefault(50, 0) + banknotesCount[1]);
            atmAvailableMoney.put(100, atmAvailableMoney.getOrDefault(100, 0) + banknotesCount[2]);
            atmAvailableMoney.put(200, atmAvailableMoney.getOrDefault(200, 0) + banknotesCount[3]);
            atmAvailableMoney.put(500, atmAvailableMoney.getOrDefault(500, 0) + banknotesCount[4]);
        }
    }

    private class MoneyWithdrawer {

        int[] withdraw(int amount) {
            if (amount > moneyCounter.count() || amount % 10 > 0) {
                return new int[]{-1};
            }
            Map<Integer, Integer> atmMoneyCopy = new HashMap<>(atmAvailableMoney);

            int[] banknotesToWithdraw = new int[5];

            if (amount / 500 > 0) {
                int numberOfBanknotesInAtm = atmMoneyCopy.get(500);
                int numberOfBanknotesNeeded = amount / 500;
                int numberOfBanknotesToWithdraw = Math.min(numberOfBanknotesInAtm, numberOfBanknotesNeeded);
                atmMoneyCopy.put(500, numberOfBanknotesInAtm - numberOfBanknotesToWithdraw);
                banknotesToWithdraw[4] = numberOfBanknotesToWithdraw;
                amount -= 500 * numberOfBanknotesToWithdraw;
            }

            if (amount / 200 > 0) {
                int numberOfBanknotesInAtm = atmMoneyCopy.get(200);
                int numberOfBanknotesNeeded = amount / 200;
                int numberOfBanknotesToWithdraw = Math.min(numberOfBanknotesInAtm, numberOfBanknotesNeeded);
                atmMoneyCopy.put(200, numberOfBanknotesInAtm - numberOfBanknotesToWithdraw);
                banknotesToWithdraw[3] = numberOfBanknotesToWithdraw;
                amount -= 200 * numberOfBanknotesToWithdraw;
            }

            if (amount / 100 > 0) {
                int numberOfBanknotesInAtm = atmMoneyCopy.get(100);
                int numberOfBanknotesNeeded = amount / 100;
                int numberOfBanknotesToWithdraw = Math.min(numberOfBanknotesInAtm, numberOfBanknotesNeeded);
                atmMoneyCopy.put(100, numberOfBanknotesInAtm - numberOfBanknotesToWithdraw);
                banknotesToWithdraw[2] = numberOfBanknotesToWithdraw;
                amount -= 100 * numberOfBanknotesToWithdraw;
            }

            if (amount / 50 > 0) {
                int numberOfBanknotesInAtm = atmMoneyCopy.get(50);
                int numberOfBanknotesNeeded = amount / 50;
                int numberOfBanknotesToWithdraw = Math.min(numberOfBanknotesInAtm, numberOfBanknotesNeeded);
                atmMoneyCopy.put(50, numberOfBanknotesInAtm - numberOfBanknotesToWithdraw);
                banknotesToWithdraw[1] = numberOfBanknotesToWithdraw;
                amount -= 50 * numberOfBanknotesToWithdraw;
            }

            if (amount / 10 > 0) {
                int numberOfBanknotesInAtm = atmMoneyCopy.get(10);
                int numberOfBanknotesNeeded = amount / 10;
                int numberOfBanknotesToWithdraw = Math.min(numberOfBanknotesInAtm, numberOfBanknotesNeeded);
                atmMoneyCopy.put(10, numberOfBanknotesInAtm - numberOfBanknotesToWithdraw);
                banknotesToWithdraw[0] = numberOfBanknotesToWithdraw;
                amount -= 10 * numberOfBanknotesToWithdraw;
            }

            if (amount > 0) {
                return new int[]{-1};
            } else {
                atmAvailableMoney = atmMoneyCopy;
            }
            return banknotesToWithdraw;
        }
    }

    public class MoneyCounter {

        public long count() {
            long sum = 0;

            Set<Integer> banknotes = atmAvailableMoney.keySet();
            for (int banknote : banknotes) {
                sum += (long) atmAvailableMoney.getOrDefault(banknote, 0) * banknote;
            }
            return sum;
        }
    }
}
