package com.sm.mongo;

public class Test
{
    public static void main(String[] args)
    {
        RentalVehicleManager sys = new WestminsterRentalVehicleManager(60) ;
        boolean exit = false ;
        while (!exit)
        {
            exit = ((WestminsterRentalVehicleManager) sys).playMenu() ;
        }


    }
}