package com.example.nomiatesttask.service;

import java.util.ArrayList;
import java.util.List;

public class ActionCounter {

    private final List<Integer> listOfCalls = new ArrayList<>();

    public void call(int timestamp) {
        listOfCalls.add(timestamp);
    }

    //int timestamp = Instant.now().getEpochSecond()
    public int getActions(int timestamp) {
        timestamp -= 300; //получаем момент времени 5 минут назад
        int size = listOfCalls.size();

        if (listOfCalls.isEmpty()) {
            return 0;
        }

        int left = 0;
        int right = size - 1;

        if(listOfCalls.get(right) < timestamp) {
            return 0;
        }
        if (listOfCalls.get(left) >= timestamp) {
            return size;
        }

        if(listOfCalls.get(size - 2) < timestamp && listOfCalls.get(size - 1) >= timestamp) {
            return 1; //частный случай, когда только последний элемент подходит
        }

        while (left <= right) {
            int middle = left + (right - left) / 2;

            if (listOfCalls.get(middle - 1) < timestamp && listOfCalls.get(middle) >= timestamp)
                return size - middle;

            if (listOfCalls.get(middle) < timestamp)
                left = middle;

            else
                right = middle;
        }

        return 0;
    }
}
