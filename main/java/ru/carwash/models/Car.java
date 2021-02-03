package ru.carwash.models;

public class Car {

    private String brand;
    private String model;
    private String carNumber;
    private String region;
    private String category;

    public Car(String brand, String model, String carNumber, String region, String category) {
        this.brand = brand;
        this.model = model;
        this.carNumber = carNumber;
        this.region = region;
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
