package com.sm.mongo;

public class Car extends Vehicle
{
    private int airbag ;
    private int numOfDoors ;

    public Car(int airbag, int numOfDoors) {
        this.airbag = airbag;
        this.numOfDoors = numOfDoors;
    }


    public Car(String noPlate, String madeIn, String model, String fuelType, int seatingCapacity, int ratesForAdditionalHours, int basicRate24Hrs, int airbag, int numOfDoors) {
        super(noPlate, madeIn, model, fuelType, seatingCapacity, ratesForAdditionalHours, basicRate24Hrs);
        this.airbag = airbag;
        this.numOfDoors = numOfDoors;
    }

    public int getAirbag() {
        return airbag;
    }

    public void setAirbag(int airbag) {
        this.airbag = airbag;
    }

    public int getNumOfDoors() {
        return numOfDoors;
    }

    public void setNumOfDoors(int numOfDoors) {
        this.numOfDoors = numOfDoors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "airbag=" + airbag +
                ", numOfDoors=" + numOfDoors + " " + super.toString() +
                '}';
    }
}
