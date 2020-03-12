package com.sm.mongo;

import java.io.IOException;

public interface RentalVehicleManager
{
    public abstract void addVehicle(Vehicle vehicle) ;
    public abstract void deleteVehicle() ;
    public abstract void editVehicle() ;
    public abstract void printList() ;
    public abstract void saveVehicle() throws IOException;
    public abstract void refreshList();
}

