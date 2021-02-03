package ru.carwash.models;

import java.util.ArrayList;

/* Заказ */
public class Order {

    private CarWash carWash;
    private Car car;
    private String data; // TODO
    private String time; // TODO
    private ArrayList<Service> services; // список услуг в заказе
    private double price;
    private int status;

    public static int CANCELED_STATUS = 0;
    public static int ACCEPTED_STATUS = 1;
    public static int COMPLETED_STATUS = 2;

    public Order(int status) {
        this.status = status;
    }

    public CarWash getCarWash() {
        return carWash;
    }

    public void setCarWash(CarWash carWash) {
        this.carWash = carWash;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
