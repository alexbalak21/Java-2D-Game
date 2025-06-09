package main;

import learning.Car;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        Car myCar = new Car("BMW", 2024);
        myCar.displayInfo();
    }
}
