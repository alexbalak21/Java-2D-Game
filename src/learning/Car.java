package learning;

// Define a simple class called Car
public class Car {
    // Attributes (fields)
    String brand;
    int year;

    // Constructor
    public Car(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    // Method to display car info
    public void displayInfo() {
        System.out.println("Brand: " + brand + ", Year: " + year);
    }
}
