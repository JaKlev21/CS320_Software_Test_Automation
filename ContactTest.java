/*
 * @author		Jonathan Kleven
 * @version		Module 3-2 Milestone Contact Service
 * @Date		1/22/2023
 */

package org.snhu.cs320.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

//https://clintonbush.notion.site/Module-3-Milestone-1-Contact-Service-c4421c03867a414fba5134be3e228bfc
//Option 2
public class ContactTest {
	private String ID = "01";
	private String FName = "Jon";
	private String LName = "LastName";
	private String PhoneNum = "1234567890";
	private String ADress = "Address";
	
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	static Stream<Arguments> defaultContactData() {
		return Stream.of(Arguments.of("01", "Bob", "LastName", "1234567890", "Address"),
				Arguments.of("37", "Jon", "LastName", "0987654321", "Address3"));
	}
	
	//Test IDValid
	//Parameterized Test from JUnit Chapter 4
	@ParameterizedTest
	@ValueSource (strings = {"01", "396", "1001"})
	void testID_Valid(String argument) {
		assertTrue(validator.validate(new Contact(argument, FName, LName, PhoneNum, ADress)).isEmpty());
	}
	
	//Test IDTooLong
	//Parameterized Test from JUnit Chapter 4
	@ParameterizedTest
	@ValueSource (strings = {"96385274101", "0987654321123", "ABCDEFGHIJKLMN"})
	void testID_TooLong(String argument) {
		assertEquals("ID cannot be longer than 10 characters",
				validator.validate(new Contact(argument, FName, LName, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//Test IDNull
	@Test
	void testID_Null() {
		assertEquals("ID is a required field",
				validator.validate(new Contact(null, FName, LName, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//Test IDBlank
	//Parameterized Test from JUnit Chapter 4
	@ParameterizedTest
	@ValueSource (strings = {"", " ", "  "})
	void testID_Blank(String argument) {
		assertEquals("ID is a required field",
				validator.validate(new Contact(argument, FName, LName, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//Test First Name Valid using method source
	//MethodSource Test from JUnit Chapter 4
	@ParameterizedTest	
	@MethodSource ("defaultContactData")
	void testMethodFirstName_Valid(String ID, String fName, String lName, String pNum, String aDress) {
		assertTrue(validator.validate(new Contact(ID, fName, lName, pNum, aDress)).isEmpty());
	}
	
	//Test First Name Valid with parameters
	//Parameterized Test from JUnit Chapter 4
	@ParameterizedTest
	@ValueSource (strings = {"Bob", "Jon", "Heather"})
	void testFirstName_Valid(String argument) {
		assertTrue(validator.validate(new Contact(ID, argument, LName, PhoneNum, ADress)).isEmpty());
	}
	
	//Test First Name is null
	@Test
	void testFirstName_Null() {
		assertEquals("First Name is a required field", 
				validator.validate(new Contact(ID, null, LName, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	// Test First name is Blank
	@ParameterizedTest
	@ValueSource (strings = {"", " ", "  "})
	void testFirstName_Blank(String argument) {
		assertEquals("First Name is a required field", 
				validator.validate(new Contact(ID, argument, LName, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//Test First Name is Too Long
	@ParameterizedTest
	@ValueSource (strings = {"BobBobBobBob", "JonJonJonJon", "HeatherHeatherHeather"})
	void testFirstName_TooLong(String argument) {
		assertEquals("First Name cannot be longer than 10 characters",
				validator.validate(new Contact(ID, argument, LName, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//Test Last Name Valid using method source
	//MethodSource Test from JUnit Chapter 4
	@ParameterizedTest	
	@MethodSource ("defaultContactData")
	void testMethodLastName_Valid(String ID, String fName, String lName, String pNum, String aDress) {
		assertTrue(validator.validate(new Contact(ID, fName, lName, pNum, aDress)).isEmpty());
	}
	
	//Test Last Name Valid with parameters
	//Parameterized Test from JUnit Chapter 4
	@ParameterizedTest
	@ValueSource (strings = {"Kleven", "James", "Chappelle"})
	void testLastName_Valid(String argument) {
		assertTrue(validator.validate(new Contact(ID, FName, argument, PhoneNum, ADress)).isEmpty());
	}
	
	//Test Last Name is null
	@Test
	void testLastName_Null() {
		assertEquals("Last Name is a required field", 
				validator.validate(new Contact(ID, FName, null, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	// Test Last name is Blank
	@ParameterizedTest
	@ValueSource (strings = {"", " ", "  "})
	void testLastName_Blank(String argument) {
		assertEquals("Last Name is a required field", 
				validator.validate(new Contact(ID, FName, argument, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//Test Last Name is Too Long
	@ParameterizedTest
	@ValueSource (strings = {"KlevenKlevenKleven", "JamesJamesJames", "ChappelleChappelleChappelle"})
	void testLastName_TooLong(String argument) {
		assertEquals("Last Name cannot be longer than 10 characters",
				validator.validate(new Contact(ID, FName, argument, PhoneNum, ADress)).stream().findFirst().get().getMessage());
	}
	
	//  Test Phone Number Valid
	@ParameterizedTest
	@ValueSource (strings = {"8604646623", "4752366315", "0987654321"})
	void testPhoneNumber_Valid(String argument) {
		assertTrue(validator.validate(new Contact(ID, FName, LName, argument, ADress)).isEmpty());
	}
	//	testPhoneHasNonDigitCharacters()
	@ParameterizedTest
	@ValueSource (strings = {"860H646623", "475236*315", "0987?54321"})
	void testPhoneNumber_HasNonDigitChar(String argument) {
		assertEquals("Phone Number must be exactly 10 digits",
				validator.validate(new Contact(ID, FName, LName, argument, ADress)).stream().findFirst().get().getMessage());
	}
	//	testPhoneIsNull()
	@Test
	void testPhoneNumber_NULL() {
		assertEquals("Phone Number is a required field",
				validator.validate(new Contact(ID, FName, LName, null, ADress)).stream().findFirst().get().getMessage());
	}
	//	testPhoneIsBlank()
	//FIXME 3rd Test Fails no matter what if String = "Phone Number must be exactly 10 digits"
	//FIXME 2nd Test Fails no matter what if String = "Phone Number is a required field"
	@ParameterizedTest
	@ValueSource (strings = {"", "", "          "})
	void testPhoneNumber_Blank(String argument) {
		assertEquals("Phone Number is a required field", 
				validator.validate(new Contact(ID, FName, LName, argument, ADress)).stream().findFirst().get().getMessage());
	}
	//	Test Phone Number Too Short
	@ParameterizedTest
	@ValueSource (strings = {"860646623", "47523615", "54321"})
	void testPhoneNumber_TooShort(String argument) {
		assertEquals("Phone Number must be exactly 10 digits",
				validator.validate(new Contact(ID, FName, LName, argument, ADress)).stream().findFirst().get().getMessage());
	}
	//	Test Phone Number is Too Long
	@ParameterizedTest
	@ValueSource (strings = {"8604444646623", "475236666666315", "098733333333333354321"})
	void testPhoneNumber_TooLong(String argument) {
		assertEquals("Phone Number must be exactly 10 digits",
				validator.validate(new Contact(ID, FName, LName, argument, ADress)).stream().findFirst().get().getMessage());
	}
	//	Test Address is Valid
	@ParameterizedTest
	@ValueSource (strings = {"531 Bleaker Street", "2 Wendy Drive, Baltic CT", "22 Oakridge Crest, Clevland OH"})
	void testAddress_Valid(String argument) {
		assertTrue(validator.validate(new Contact(ID, FName, LName, PhoneNum, argument)).isEmpty());
	}
	//	Test Address is Null
	@Test
	void testAddress_NULL() {
		assertEquals("Address is a required field",
				validator.validate(new Contact(ID, FName, LName, PhoneNum, null)).stream().findFirst().get().getMessage());
	}
	//	Test Address is Blank
	@ParameterizedTest
	@ValueSource (strings = {"", " ", "  "})
	void testAddress_Blank(String argument) {
		assertEquals("Address is a required field", 
				validator.validate(new Contact(ID, FName, LName, PhoneNum, argument)).stream().findFirst().get().getMessage());
	}
	// 	Test Address is Too Long
//	Test Address is Valid
	@ParameterizedTest
	@ValueSource (strings = {"531 Bleaker Street, London England", "2235649879865 Wendy Drive, Baltic CT", "2212345678901254 Oakridge Crest, Clevland OH 06335"})
	void testAddress_TooLong(String argument) {
		assertEquals("Address cannot be longer than 30 characters", 
				validator.validate(new Contact(ID, FName, LName, PhoneNum, argument)).stream().findFirst().get().getMessage());
	}


}
