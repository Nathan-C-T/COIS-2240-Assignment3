import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;



class VehicleRentalTest {

	@Test
	void testValidLicensePlate() {
		
		Car c1 =new Car("Honda","CRV",2007,5);
		Car c2 =new Car("Honda","CRV",2007,5);
		Car c3 =new Car("Honda","CRV",2007,5);
		
		assertDoesNotThrow(() -> c1.setLicensePlate("AAA100"));
		assertDoesNotThrow(() -> c2.setLicensePlate("ABC567"));
		assertDoesNotThrow(() -> c3.setLicensePlate("ZZZ999"));
	}
	
	@Test
	void testInvalidLicensePlate1() {
		Car c4 =new Car("Honda","CRV",2007,5);
		assertThrows(IllegalArgumentException.class, () -> c4.setLicensePlate(null));
	}
	@Test
	void testInvalidLicensePlate2() {	
		Car c5 =new Car("Honda","CRV",2007,5);
		assertThrows(IllegalArgumentException.class, () -> c5.setLicensePlate(""));
	}

	@Test
	void testInvalidLicensePlate3() {		
		Car c6 =new Car("Honda","CRV",2007,5);
		assertThrows(IllegalArgumentException.class, () -> c6.setLicensePlate("ZZZ99"));
	}	
	
	@Test
	void testRentAndReturnVehicle() {
		Car c1 =new Car("Honda","CRV",2007,5);
		c1.setLicensePlate("AAA100");
		assertEquals(Vehicle.VehicleStatus.Available, c1.getStatus());

		Customer c = new Customer(262,"Bob");

		RentalSystem rentalSystem = RentalSystem.getInstance();
		
		//rent 
		boolean rented = rentalSystem.rentVehicle(c1, c, LocalDate.now(), 100.0);
		assertTrue(rented);
		assertEquals(Vehicle.VehicleStatus.Rented, c1.getStatus());
		
		boolean reRent = rentalSystem.rentVehicle(c1, c, LocalDate.now(), 100.0);
		assertFalse(reRent);
		
		boolean returned = rentalSystem.returnVehicle(c1, c, LocalDate.now(), 0);
		assertTrue(returned);
		assertEquals(Vehicle.VehicleStatus.Available, c1.getStatus());
		
		boolean reReturned = rentalSystem.returnVehicle(c1, c, LocalDate.now(), 0);
		assertFalse(reReturned );
		
	}
	
	@Test
    void testSingletonRentalSystem()throws Exception {

        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();

        int modifiers = constructor.getModifiers();
        assertTrue(Modifier.isPrivate(modifiers), "RentalSystem constructor is private");

        //singleton instance
        RentalSystem instance = RentalSystem.getInstance();
        assertNotNull(instance, "should return a non-null RentalSystem instance");
    }
	
	
}

