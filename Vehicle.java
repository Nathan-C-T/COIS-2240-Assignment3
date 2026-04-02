public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { Available, Held, Rented, UnderMaintenance, OutOfService }

    public Vehicle(String make, String model, int year) {
    	/*if (make == null || make.isEmpty())
    		this.make = null;
    	else
    		this.make = make.substring(0, 1).toUpperCase() + make.substring(1).toLowerCase();
    	
    	if (model == null || model.isEmpty())
    		this.model = null;
    	else
    		this.model = model.substring(0, 1).toUpperCase() + model.substring(1).toLowerCase();
    	*/
    	this.make =capitalize(make);
    	this.model=capitalize(model);
        this.year = year;
        this.status = VehicleStatus.Available;
        this.licensePlate = null;
    }

    public Vehicle() {
        this(null, null, 0);
    }
    
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return null; // preserves your current behavior of setting null
        }
        return input.substring(0, 1).toUpperCase() +input.substring(1).toLowerCase();
    }
    
    //task2 -pt1
    private boolean isValidPlate(String plate){
    	boolean valid= true;
    	if (plate != null && plate.isEmpty() ==false && plate.length() ==6) {
    		for(int i=0;i<6;i++) {
    			if(i<3) {
    				valid =valid && Character.isLetter(plate.charAt(i));
    			} else {
    				valid =valid && Character.isDigit(plate.charAt(i));
    			}
    	}
    		
    	}
    	return valid;
    }
    

    public void setLicensePlate(String plate) {
    	if(isValidPlate(plate)) {    		
    		this.licensePlate = plate == null ? null : plate.toUpperCase();
		} else {
			throw new IllegalArgumentException("[FAILED TO SET PLATE] - invalid plate format, must be 3 letters followed by 3 numbers"); 
		}
    }

    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

}
