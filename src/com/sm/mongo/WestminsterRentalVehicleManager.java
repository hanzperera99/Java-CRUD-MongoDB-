package com.sm.mongo;

import com.mongodb.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterRentalVehicleManager implements RentalVehicleManager
{

    //Creating the mongo object to connect the java code with the mongo server
    MongoClient mongoClient = new MongoClient ( "localhost" , 27017 ) ;

    //Getting the VehicleRental database from the mongodb database
    DB dbs = mongoClient.getDB("VehicleRental");

    private int numObject ;
    private ArrayList<Vehicle> list ;
    private ArrayList<String> compare_list_car ;
    private ArrayList<String> compare_list_bike ;
    private ArrayList<String> compare_list_booking ;
    private ArrayList<String> car_validation_list ;
    private ArrayList<String> bike_validation_list ;
    //private ArrayList<String> car_saving_list ;

    public WestminsterRentalVehicleManager(int numObject)
    {
        this.numObject = numObject ;
        list = new ArrayList<Vehicle>()  ;
    }

    public void addVehicle(Vehicle vehicle)
    {
        list.add(vehicle) ;
        //Getting the two collections which are created in the VehicleRental database to add the Vehicle objects created in the CLI
        DBCollection cars = dbs.getCollection("Cars");
        DBCollection bikes = dbs.getCollection("Bikes");

        Cursor cursor1 = cars.find();
        Cursor cursor2 = bikes.find();

        //creating a BasicDBObject for inserting to the database.


        if(list.size() < numObject )
        {
            if(vehicle instanceof Car){
                car_validation_list = new ArrayList<String>() ;
                while (cursor1.hasNext())
                {
                    String included_vehicles = (String) cursor1.next().get("plateNo");
                    car_validation_list.add(included_vehicles) ;
                }
                String checker = "" ;
                for(int k = 0 ; k <list.size() ; k++)
                {
                    for(int i = 0 ; i < car_validation_list.size() ; i++)

                    {
                        if(list.get(k).getNoPlate().equals(car_validation_list.get(i)))
                        {
                            checker = "y" ;
                            break;
                        }
                    }
                }

                if(!checker.equals("y"))
                {
                    BasicDBObject document = new BasicDBObject();
                    if(vehicle instanceof Car) {
                        addGeneralFields(vehicle, document);
                        document.put("NumberOfAirBags",((Car) vehicle).getAirbag());
                        document.put("NumberOfDoors",((Car) vehicle).getNumOfDoors());
                        cars.insert(document) ;
                    }
                }
                else {
                    System.out.println("You've entered a vehicle with this plate number. Just check the plate number again:");
                }
            }

            if (vehicle instanceof Bike){
                bike_validation_list = new ArrayList<String>() ;
                while (cursor2.hasNext())
                {
                    String included_vehicles_bike = (String) cursor2.next() .get("plateNo");
                    bike_validation_list.add(included_vehicles_bike) ;
                }
                String checker_bike = "" ;
                for(int k = 0 ; k <list.size() ; k++)
                {
                    for(int i = 0 ; i < bike_validation_list.size() ; i++)

                    {
                        if(list.get(k).getNoPlate().equals(bike_validation_list.get(i)))
                        {
                            checker_bike = "y" ;
                            break;
                        }
                    }
                }

                if(!checker_bike.equals("y"))
                {
                    BasicDBObject document = new BasicDBObject();

                    if(vehicle instanceof Bike)
                    {
                        addGeneralFields(vehicle, document);
                        document.put("BikeType",((Bike) vehicle).getBikeType());
                        document.put("HelmetType",((Bike) vehicle).getHelmetType());
                        bikes.insert(document) ;
                    }
                }
                else {
                    System.out.println("You've entered a vehicle with this plate number. Just check the plate number again:");
                }
            }

        }
        else
        {
            System.out.println("No more space available in the parking area");
        }
    }

    /*These are the common attributes for both of Vehicle types. So I created a method
    without repeating the same set of executing codes in above code block*/

    private void addGeneralFields(Vehicle vehicle, BasicDBObject document) {
        document.put("plateNo",vehicle.getNoPlate());
        document.put("make",vehicle.getMadeIn());
        document.put("model",vehicle.getModel());
        document.put("RateForOneDay",vehicle.getBasicRate24Hrs());
        document.put("RateForAdditionalOneOur",vehicle.getRatesForAdditionalHours());
        document.put("FuelType",vehicle.getFuelType());
        document.put("SeatingCapacity",vehicle.getSeatingCapacity());
    }




    public void deleteVehicle()
    {
        Scanner input01 = new Scanner(System.in) ;
        System.out.print("You want to delete a car or a bike ? ") ;
        String delete_vehicleType = input01.nextLine().toLowerCase() ;

        if(delete_vehicleType.equals("bike"))
        {
            DBCollection bikes = dbs.getCollection("Bikes");
            Scanner input3 = new Scanner(System.in) ;
            System.out.println("What is the vehicle that you need to delete (Enter the plate number) ? ");
            String manager_delete = input3.nextLine() ;

            BasicDBObject delete_bike = new BasicDBObject("plateNo", manager_delete);
            bikes.remove(delete_bike);
        }

        if(delete_vehicleType.equals("car"))
        {
            DBCollection cars = dbs.getCollection("Cars");
            Scanner input3 = new Scanner(System.in) ;
            System.out.println("What is the vehicle that you need to delete (Enter the plate number) ? ");
            String manager_delete = input3.nextLine() ;

            BasicDBObject delete_car = new BasicDBObject("plateNo", manager_delete);
            cars.remove(delete_car);
        }
    }




    public void editVehicle()
    {
        System.out.println("Here is the vehicle list..Select the vehicle that you need to be updated") ;
        for(int i=0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }

        System.out.println();
        Scanner input3 = new Scanner(System.in) ;
        System.out.print("Which vehicle you need to edit ? (Enter the index) ");
        int manager_edit = input3.nextInt() ;

        System.out.println();
        System.out.println("This is the vehicle that you need to edit");
        System.out.println(list.get(manager_edit));
        System.out.println();

        Scanner input4 = new Scanner(System.in) ;
        System.out.print("Whats the new No plate : ");
        String manager_numPlate = input4.nextLine() ;
        list.get(manager_edit).setNoPlate(manager_numPlate) ;
        System.out.println(list.get(manager_edit).toString());
    }




    public void printList()
    {
        DBCollection cars = dbs.getCollection("Cars");
        DBCollection bikes = dbs.getCollection("Bikes");

        Cursor cursor1 = cars.find() ;
        Cursor cursor2 = bikes.find() ;

        System.out.println();
        System.out.println("All the cars that are available upto this moment");
        while (cursor1.hasNext())
        {
            System.out.println(cursor1.next());
        }
        System.out.println();
        System.out.println("All the bikes that are available upto this moment");

        while (cursor2.hasNext())
        {
            System.out.println(cursor2.next());
        }
        System.out.println();
    }




     public void saveVehicle()
     {
                try
                {
                File file = new File("fileCar.txt") ;
                File file_bike = new File("fileBike.txt") ;

                if(!file.exists())
                {
                    file.createNewFile();
                }
                DBCollection cars = dbs.getCollection("Cars");
                DBCollection bikes = dbs.getCollection("Bikes");

                Cursor cursor1 = cars.find() ;
                Cursor cursor2 = bikes.find() ;


                PrintWriter pw = new PrintWriter(file) ;
                while (cursor1.hasNext())
                {
                    pw.println(cursor1.next());
                }
                pw.close();

                PrintWriter pw_bike = new PrintWriter(file_bike) ;
                while (cursor2.hasNext())
                {
                    pw_bike.println(cursor2.next());
                }
                pw_bike.close();


                }catch (IOException e)
                {
                    e.printStackTrace();
                }
     }




     public void refreshList()
     {
         DBCollection bookedvehicles = dbs.getCollection("booked_vehicles");
         DBCollection cars = dbs.getCollection("Cars");
         DBCollection bikes = dbs.getCollection("Bikes");


         Cursor cursor1 = cars.find() ;

         Cursor cursor2 = bikes.find() ;

         Cursor cursor3 = bookedvehicles.find() ;

         compare_list_car = new ArrayList<String>() ;
         while(cursor1.hasNext())
         {
             String cursor_1 = (String) cursor1.next().get("plateNo");
             compare_list_car.add(cursor_1) ;
         }

         compare_list_bike = new ArrayList<String>();
         while (cursor2.hasNext())
         {
             String cursor_2 = (String) cursor2.next().get("plateNo");
             compare_list_car.add(cursor_2) ;
         }

         compare_list_booking = new ArrayList<String>() ;
         while (cursor3.hasNext())
         {
             String cursor_3 = (String) cursor3.next().get("plateNo");
             compare_list_booking.add(cursor_3);
         }

         /*comparing items*/

         for (int i = 0 ; i < compare_list_booking.size() ; i ++)
         {
             for (int j = 0 ; j < compare_list_car.size() ; j ++)
             {
                 if (compare_list_booking.get(i).equals(compare_list_car.get(j)))
                 {
                     System.out.println(compare_list_car.get(j) + " - is booked");
                 }
             }
         }

         for(int i = 0 ; i < compare_list_booking.size() ; i ++)
         {
             for(int j = 0 ; j < compare_list_car.size() ; j ++)
             {
                 if (compare_list_booking.get(i).equals(compare_list_car.get(j)))
                 {
                     BasicDBObject del_document_car = new BasicDBObject("plateNo" , compare_list_booking.get(i)) ;
                     cars.remove(del_document_car) ;
                     System.out.println("deleted till customer brings the vehicle");
                 }
             }
         }

         for (int i = 0 ; i < compare_list_booking.size() ; i ++)
         {
             for (int j = 0 ; j < compare_list_bike.size() ; j ++)
             {
                 if (compare_list_booking.get(i).equals(compare_list_bike.get(j)))
                 {
                     System.out.println(compare_list_bike.get(j) + " - is booked");
                 }
             }
         }

         for(int i = 0 ; i < compare_list_booking.size() ; i ++)
         {
             for(int j = 0 ; j < compare_list_bike.size() ; j ++)
             {
                 if (compare_list_booking.get(i).equals(compare_list_bike.get(j)))
                 {
                     BasicDBObject del_document_bike = new BasicDBObject("plateNo" , compare_list_booking.get(i)) ;
                     bikes.remove(del_document_bike) ;
                     System.out.println("deleted till customer brings the vehicle");
                 }
             }
         }

     }




    public boolean playMenu()
    {
        boolean exit = false ;

        System.out.println();
        System.out.println("----------------------------------------------------------------------------Westminster Rental Management System---------------------------------------------------------------------------------------");
        System.out.println();


        Scanner input5 = new Scanner(System.in) ;
        int user_input ;
        while (true)
        {
            try{
                System.out.println("Enter 1 to 'Add' vehicles");
                System.out.println("Enter 2 to 'Edit' vehicles");
                System.out.println("Enter 3 to 'Delete' vehicles");
                System.out.println("Enter 4 to 'Save' the list");
                System.out.println("Enter 5 to 'Refresh' the data base");
                System.out.println("Enter 6 to 'Print' the existing database");
                System.out.println("Enter 7 to 'Exit' from the program");
                user_input = input5.nextInt() ;
                System.out.println();
                break;
            }catch (Exception e){
                System.out.println("Please enter a number for the menu selection" );
                input5.nextLine() ;
                System.out.println();
            }
        }

        switch (user_input)
        {
            case 1 :

                Scanner input6 = new Scanner(System.in) ;
                int manager_vehicle ;
                while (true)
                {
                    try{
                        System.out.println("You want to enter a car or a bike");
                        System.out.println("To enter a car enter 1 ");
                        System.out.println("To enter a bike enter 2 ");
                        manager_vehicle = input6.nextInt() ;
                        System.out.println();
                        break;
                    }catch (Exception e){
                        System.out.println("-----------------Please enter a number for the above field-----------------" );
                        input6.nextLine() ;
                        System.out.println();
                    }
                }

                switch (manager_vehicle)
                {
                    case 1 :
                        System.out.println("You are going to enter a car");

                        String manager_numplate;
                        Scanner input7 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the number plate of the car : ");
                            manager_numplate = input7.nextLine();
                            System.out.println();
                            if (manager_numplate.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                             {
                                 System.out.println();
                                 break;
                             }
                        }

                        String manager_madeIn ;
                        Scanner input8 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the produced country : ");
                            manager_madeIn  = input8.nextLine();
                            System.out.println();
                            if (manager_madeIn.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        String manager_model ;
                        Scanner input9 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the model of the car : ");
                            manager_model  = input9.nextLine();
                            System.out.println();
                            if (manager_model.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }


                        String manager_fuelType ;
                        Scanner input10 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the fuel type of the car : ");
                            manager_fuelType  = input10.nextLine();
                            System.out.println();
                            if (manager_fuelType.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        Scanner input11 = new Scanner(System.in) ;
                        int manager_seatingCapacity ;
                        while (true) {
                            try {
                                do {
                                    System.out.print("Enter the seating capacity of the car : ");
                                    manager_seatingCapacity = input11.nextInt() ;
                                }while (manager_seatingCapacity <= 0);
                                System.out.println();
                                System.out.println();
                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input11.nextLine();
                                System.out.println();
                            }
                        }

                        Scanner input12 = new Scanner(System.in) ;
                        int manager_ratesForAdditionalHours ;
                        while (true) {
                            try {
                                do {
                                    System.out.print("Enter the rent for additional one hour : ");
                                    manager_ratesForAdditionalHours = input12.nextInt() ;
                                }while (manager_ratesForAdditionalHours <= 0);
                                System.out.println();
                                System.out.println();
                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input12.nextLine();
                                System.out.println();
                            }
                        }

                        Scanner input13 = new Scanner(System.in) ;
                        int  manager_basicRate24Hrs ;
                        while (true)
                        {
                            try{
                                do {
                                    System.out.print("Enter the basic rate for 24Hrs for the car : ");
                                    manager_basicRate24Hrs = input13.nextInt() ;
                                }while (manager_basicRate24Hrs <= 0);
                                System.out.println();
                                System.out.println();
                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input13.nextLine() ;
                                System.out.println();
                            }
                        }

                        Scanner input14 = new Scanner(System.in) ;
                        int manager_airbag ;
                        while (true)
                        {
                            try{
                                do {
                                    System.out.print("How many air bags in the car : ");
                                    manager_airbag = input14.nextInt() ;
                                }while (manager_airbag <= 0);
                                System.out.println();
                                System.out.println();
                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input14.nextLine() ;
                                System.out.println();
                            }
                        }

                        Scanner input15 = new Scanner(System.in) ;
                        int manager_numOfDoors ;
                        while (true)
                        {
                            try{
                                do {
                                    System.out.print("How many doors in the car : ");
                                    manager_numOfDoors = input15.nextInt() ;
                                }while (manager_numOfDoors <= 0);
                                System.out.println();
                                System.out.println();
                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input15.nextLine() ;
                                System.out.println();
                            }
                        }

                        Car car = new Car(manager_numplate, manager_madeIn , manager_model ,manager_fuelType, manager_seatingCapacity
                                , manager_ratesForAdditionalHours , manager_basicRate24Hrs , manager_airbag , manager_numOfDoors) ;

                        addVehicle(car);
                        break;

                    case 2 :

                        System.out.println("You are going to enter a bike");

                        String manager_numplate_bike ;
                        Scanner input16 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the number plate of the bike : ");
                            manager_numplate_bike  = input16.nextLine();
                            System.out.println();
                            if (manager_numplate_bike.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }


                        String manager_madeIn_bike;
                        Scanner input17 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the produced country : ");
                            manager_madeIn_bike  = input17.nextLine();
                            System.out.println();
                            if (manager_madeIn_bike.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        String manager_model_bike;
                        Scanner input18 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the model of the bike : ");
                            manager_model_bike  = input18.nextLine();
                            System.out.println();
                            if (manager_model_bike.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        String manager_fuelType_bike;
                        Scanner input19 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("Enter the fuel type of the bike : ");
                            manager_fuelType_bike  = input19.nextLine();
                            System.out.println();
                            if (manager_fuelType_bike.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        Scanner input20 = new Scanner(System.in) ;
                        int manager_seatingCapacity_bike ;
                        while (true)
                        {
                            try{
                                do {
                                    System.out.print("Enter the seating capacity of the bike : ");
                                    manager_seatingCapacity_bike = input20.nextInt() ;
                                }while (manager_seatingCapacity_bike <= 0);
                                System.out.println();

                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input20.nextLine() ;
                                System.out.println();
                            }
                        }

                        Scanner input21 = new Scanner(System.in) ;
                        int manager_ratesForAdditionalHours_bike ;
                        while (true)
                        {
                            try{
                                do {
                                    System.out.print("Enter the rate for addition one hour : ");
                                    manager_ratesForAdditionalHours_bike = input21.nextInt() ;
                                }while (manager_ratesForAdditionalHours_bike <= 0);
                                System.out.println();

                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input21.nextLine() ;
                                System.out.println();
                            }
                        }

                        Scanner input22 = new Scanner(System.in) ;
                        int manager_basicRate24Hrs_bike ;
                        while (true)
                        {
                            try{
                                do {
                                    System.out.print("Enter the rates for a one day : ");
                                    manager_basicRate24Hrs_bike = input22.nextInt() ;
                                }while (manager_basicRate24Hrs_bike <= 0);
                                System.out.println();

                                break;
                            }catch (Exception e){
                                System.out.println("-----------------Please enter a number for the above field-----------------" );
                                input22.nextLine() ;
                                System.out.println();
                            }
                        }

                        String manager_bike_type ;
                        Scanner input23 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("What is the type of the bike : ");
                            manager_bike_type  = input23.nextLine();
                            System.out.println();
                            if (manager_bike_type.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        String manager_helmet_type ;
                        Scanner input24 = new Scanner(System.in) ;
                        while (true)
                        {
                            System.out.print("What is the helmet type of the bike : ");
                            manager_helmet_type  = input24.nextLine();
                            System.out.println();
                            if (manager_helmet_type.length() == 0) {
                                System.out.println("Don't insert a null value...Try again");
                            }
                            else
                            {
                                System.out.println();
                                break;
                            }
                        }

                        Bike bike = new Bike(manager_numplate_bike, manager_madeIn_bike , manager_model_bike ,manager_fuelType_bike, manager_seatingCapacity_bike
                                , manager_ratesForAdditionalHours_bike , manager_basicRate24Hrs_bike , manager_bike_type , manager_helmet_type) ;

                        addVehicle(bike);
                        break;
                }
                break;

            case 2 :
                editVehicle();
                break;

            case 3 :
                deleteVehicle();
                break;

            case 4 :
                saveVehicle();
                break;

            case 5 :
                refreshList();
                break;

            case 6 :
                printList();
                break;

            case 7 :
                exit = true ;
                break;
        }
        return exit ;
    }

}