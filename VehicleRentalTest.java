import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
	
	
	
}

