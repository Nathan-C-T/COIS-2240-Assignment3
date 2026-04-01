import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;


public class RentalSystem {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    //instance variable
    private static RentalSystem instance;
    
    //make the constructor private 
    private RentalSystem() {
//    	loadData(); //add previously saved info
    }
    

    // Public accessor method
    public static RentalSystem getInstance() {
    if (instance == null) {
    	instance = new RentalSystem();
    	
    }
    return instance;
    }
    
    public void saveVehicle(Vehicle vehicle){
    	String vInfo = vehicle.getLicensePlate()+","+ vehicle.getMake()+","+ vehicle.getModel()+","+ vehicle.getYear() +","+ vehicle.getStatus();

    	//add vehicle type and attributes
    	if (vehicle instanceof Car) {
    		Car c = (Car) vehicle;
    		vInfo = "1," +vInfo +","+ c.getNumSeats();
        } else if (vehicle instanceof Minibus) {
        	Minibus mb = (Minibus) vehicle;
        	vInfo = "2," +vInfo+"," + mb.getAccessibility();
        } else if (vehicle instanceof PickupTruck) {
        	PickupTruck pt = (PickupTruck) vehicle;
        	vInfo = "3," +vInfo +","+ pt.getCargoSize() +","+ pt.hasTrailer();
        } else {
        	vInfo = "0,"+vInfo;
        }
    	
    	try {
    		FileWriter fileWrite =new FileWriter("vehicles.txt",true);
    		fileWrite.write(vInfo+"\n"); //    	[type,plate,make,model,year,status,attribute1,opt(appribute2)]\n
    		// Close the file
            fileWrite.close();
    	}catch(IOException exept){
    		System.out.println("Error writing to the file");
    	}
    }
    
    public void saveCustomer(Customer customer){
    	String cInfo = customer.getCustomerId()+","+ customer.getCustomerName();
    	try {
    		FileWriter fileWrite =new FileWriter("customers.txt",true);
    		fileWrite.write(cInfo+"\n");
            // Close the file
            fileWrite.close();
    	}catch(IOException exept){
    		System.out.println("Error writing to the file");
    	}
    }
    
    public void saveRecord(RentalRecord record){
    	String rInfo = record.getRecordType() + "," + record.getVehicle().getLicensePlate() + "," + record.getCustomer().getCustomerId() + "," + record.getRecordDate() + "," + record.getTotalAmount();
    	try {
    		FileWriter fileWrite =new FileWriter("rental_records.txt",true);
    		fileWrite.write(rInfo+"\n");
            // Close the file
            fileWrite.close();
    	}catch(IOException exept){
    		System.out.println("Error writing to the file");
    	}
    }
    
    public void vehiclesFileUpdate() {
    	
        try {
        	//overriding - (not append)
            FileWriter fileWrite = new FileWriter("vehicles.txt"); // overwrite mode
            for (Vehicle v : vehicles) {
            	
            	String vInfo = v.getLicensePlate()+","+ v.getMake()+","+ v.getModel()+","+ v.getYear() +","+ v.getStatus();

            	//add vehicle type and attributes
            	if (v instanceof Car) {
            		Car c = (Car) v;
            		vInfo = "1," +vInfo +","+ c.getNumSeats();
                } else if (v instanceof Minibus) {
                	Minibus mb = (Minibus) v;
                	vInfo = "2," +vInfo+"," + mb.getAccessibility();
                } else if (v instanceof PickupTruck) {
                	PickupTruck pt = (PickupTruck) v;
                	vInfo = "3," +vInfo +","+ pt.getCargoSize() +","+ pt.hasTrailer();
                } else {
                	vInfo = "0,"+vInfo;
                }
            	
            	fileWrite.write(vInfo+"\n");
            }
            fileWrite.close();
        } catch (IOException exept) {
            System.out.println("Error updating vehicles file");
        }
    }
    

    
    
    
    public void addVehicle(Vehicle vehicle) {
    	vehicles.add(vehicle);
    	saveVehicle(vehicle);
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);
            vehiclesFileUpdate();
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);
            RentalRecord record =new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);
            vehiclesFileUpdate();
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(Vehicle.VehicleStatus status) {
        // Display appropriate title based on status
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }
        
        // Header with proper column widths
        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    	  
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            // Header with proper column widths
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");
            
            for (RentalRecord record : rentalHistory.getRentalHistory()) {                
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }
}