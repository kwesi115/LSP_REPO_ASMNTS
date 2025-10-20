package org.howard.edu.lsp.midterm.question2;

/**
 * Demonstrates method overloading in AreaCalculator.
 */
public class Main {
    public static void main(String[] args) {
        // Required output
        System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

        try {
            System.out.println("Attempting invalid call...");
            System.out.println(AreaCalculator.area(-5.0)); // should throw exception
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

/*
 * Explanation:
 * Using method overloading is cleaner than creating separate names
 * like rectangleArea or circleArea because all these methods
 * perform the same kind of task — calculating an area. Overloading
 * keeps the API simple and easier to read since the parameter
 * types show which shape is being calculated.
 */