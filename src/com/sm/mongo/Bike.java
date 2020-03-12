package com.sm.mongo;

    public class Bike extends Vehicle
    {
        private String bikeType ;
        private String helmetType ;

        public Bike(String bikeType, String helmetType) {
            this.bikeType = bikeType;
            this.helmetType = helmetType;
        }

        public Bike(String noPlate, String madeIn, String model, String fuelType, int seatingCapacity, int ratesForAdditionalHours, int basicRate24Hrs, String bikeType, String helmetType) {
            super(noPlate, madeIn, model, fuelType, seatingCapacity, ratesForAdditionalHours, basicRate24Hrs);
            this.bikeType = bikeType;
            this.helmetType = helmetType;
        }

        public String getBikeType() {
            return bikeType;
        }

        public String getHelmetType() {
            return helmetType;
        }

        @Override
        public String toString() {
            return "Bike{" +
                    "bikeType='" + bikeType + '\'' +
                    ", helmetType='" + helmetType + '\'' + " " + super.toString() +
                    '}';
        }
    }
