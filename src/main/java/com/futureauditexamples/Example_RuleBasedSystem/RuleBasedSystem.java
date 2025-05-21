package com.futureauditexamples.Example_RuleBasedSystem;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RuleBasedSystem {

    public static void main(String[] args) {

        // Get the current time
        LocalTime timeNow = LocalTime.now();
        int hour = timeNow.getHour();

        // Format the time nicely (e.g., 14:05)
        String formattedTime = timeNow.format(DateTimeFormatter.ofPattern("HH:mm"));

        // Print the current time
        System.out.println("The current time is: " + formattedTime);

        // Apply simple rules based on the hour
        if (hour >= 5 && hour < 12) {
            System.out.println("Good morning!");
        } else if (hour >= 12 && hour < 18) {
            System.out.println("Good afternoon!");
        } else {
            System.out.println("Good evening!");
        }
    }

}
