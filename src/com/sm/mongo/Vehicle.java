package com.sm.mongo;

public class Vehicle
{
    private String noPlate ;
    private String madeIn ;
    private String model ;
    private String fuelType ;
    private int seatingCapacity ;
    private int ratesForAdditionalHours ;
    private int basicRate24Hrs ;

    public Vehicle()
    {

    }

    public Vehicle(String noPlate, String madeIn, String model, String fuelType, int seatingCapacity, int ratesForAdditionalHours, int basicRate24Hrs) {
        this.noPlate = noPlate;
        this.madeIn = madeIn;
        this.model = model;
        this.fuelType = fuelType;
        this.seatingCapacity = seatingCapacity;
        this.ratesForAdditionalHours = ratesForAdditionalHours;
        this.basicRate24Hrs = basicRate24Hrs;
    }

    public String getNoPlate() {
        return noPlate;
    }

    public void setNoPlate(String noPlate) {
        this.noPlate = noPlate;
    }

    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public int getRatesForAdditionalHours() {
        return ratesForAdditionalHours;
    }

    public void setRatesForAdditionalHours(int ratesForAdditionalHours) {
        this.ratesForAdditionalHours = ratesForAdditionalHours;
    }

    public int getBasicRate24Hrs() {
        return basicRate24Hrs;
    }

    public void setBasicRate24Hrs(int basicRate24Hrs) {
        this.basicRate24Hrs = basicRate24Hrs;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "noPlate='" + noPlate + '\'' +
                ", madeIn='" + madeIn + '\'' +
                ", model='" + model + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", ratesForAdditionalHours=" + ratesForAdditionalHours +
                ", basicRate24Hrs=" + basicRate24Hrs +
                '}';
    }
}
