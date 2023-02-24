/*
 * @author		Jonathan Kleven
 * @version		Module 3-2 Milestone Contact Service
 * @Date		1/22/2023
 */


package org.snhu.cs320.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.snhu.cs320.entities.Contact;

import jakarta.validation.ValidationException;

public class ContactServiceTest {
		
	ContactService service;
	@BeforeEach
	void init() {
		ContactService.INSTANCE = null;
		service = ContactService.getInstance();
		service.contactRepository.putAll(Map.of(
				"100001", new Contact("100001", "Jon", "Kleven", "8602358597", "2 Wendy Drive, Baltic"),
				"100002", new Contact("100002", "Heather", "Kleven", "8609615222", "2 Wendy Drive Baltic"),
				"100003", new Contact("100003", "Amelia", "Kleven", "0123456789", "2 Wendy Drive Baltic"),
				"100004", new Contact("100004", "Stuart", "Pollin", "9876543210", "Ledyard, CT"),
				"100005", new Contact("100005", "Duane", "Kleven", "9638527410", "Mayville, ND")
				));
		//System.out.println("Current Map");
		//System.out.println(service.contactRepository);
	}
	
	// Test Create Null
	@Test
	public void create_PreventNull() {
		assertThrows(NullPointerException.class, () -> service.create(null));
	}
	
	// Test Create duplicate
	@Test
	public void create_ExistingContact() {
		assertThrows(
				IllegalArgumentException.class,
				() -> service.create(new Contact("100001", "Jon", "Kleven", "8602358597", "2 Wendy Drive, Baltic"))
				);
	}

	// Test Create Valid/Successful
	@Test
	public void create_Valid() {
		service.create(new Contact("100006", "NewFirst", "NewLast", "1011121314", "NewAddress"));
		assertThat(service.contactRepository).containsKey("100006");
	}
	
	// Test Create Invalid entity
	@Test
	public void create_InvalidEntry() {
		assertThrows(ValidationException.class, () -> service.create(new Contact("", "", "", "", "")));
	}
	
	// Test Delete if ID Exists
	@ParameterizedTest
	@ValueSource (strings = {"100001", "100003","100005"})
	public void deleteById_IDExists(String argument) {
		assertThat(service.deleteById(argument))
		.isNotEmpty()
		.get()
		.extracting(p -> p.id())
		.isEqualTo(argument);
	
		assertThat(service.contactRepository)
		.doesNotContainKey(argument);
	}
	
	// Test Delete if ID does Not Exist
	@ParameterizedTest
	@ValueSource (strings = {"100000", "1", "9999999999"})
	public void deleteById_IDDoesNotExist(String argument) {
		assertThat(service.deleteById(argument)).isEmpty();
	}
	
	// Test Delete if Invalid ID is given
	//FIXME argument fails this test "ABCDEFGHIJKLMNOP"
	@ParameterizedTest
	@ValueSource (strings = {"  ", "\n\n\r\r",""})
	public void deleteById_IDInvalidGiven(final String argument) {
		assertThrows(IllegalArgumentException.class, () -> service.deleteById(argument));
	}
	
	// Test Delete with Null ID
	@Test
	public void deleteById_NullId() {
		assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
	}
	
	// Test Find if ID exists
	@ParameterizedTest
	@ValueSource (strings = {"100001", "100003","100005"})
	public void findById_IDExists(String argument) {
		assertThat(service.findById(argument))
		.isNotEmpty()
		.get()
		.extracting(p -> p.id())
		.isEqualTo(argument);			
	}
	
	// Test Find if ID Does Not Exist
	@ParameterizedTest
	@ValueSource (strings = {"100007", "100009","100011"})
	public void findById_IDDoesNotExists(String argument) {
		assertThat(service.findById(argument)).isEmpty();				
	}
	
	// Test Find if Invalid ID is Given
	@ParameterizedTest
	@ValueSource (strings = {"     ", "\n\n\r\r",""})
	public void findById_IDInvalidGiven(final String argument) {
		assertThrows(IllegalArgumentException.class, () -> service.findById(argument));
	}
	
	// Test Find with Null ID
	@Test
	public void findById_NullGiven() {
		assertThrows(IllegalArgumentException.class, () -> service.findById(null));
	}
	
	// Test Update First Name Only
	@ParameterizedTest
	@CsvSource({"100001,Noj", "100003,Ailema", "100002,Rehtaeh"})
	public void update_UpdateFirstName(String ID, String NewName) {
		//service.contactRepository.get(ID).lastName();
		assertThat(service.update(new Contact(ID, NewName, service.contactRepository.get(ID).lastName(), service.contactRepository.get(ID).phoneNumber(), service.contactRepository.get(ID).address())))
		.get()
		.extracting(p -> p.firstName())
		.isEqualTo(NewName);
		//System.out.println(service.contactRepository.get(ID));
	}
		
	// Test Update Last Name
	@ParameterizedTest
	@CsvSource({"100001,WasKleven", "100003,WasKleven", "100002,WasKleven"})
	public void update_UpdateLastName(String ID, String NewName) {
		service.contactRepository.get(ID).lastName();
		assertThat(service.update(new Contact(ID, service.contactRepository.get(ID).firstName(), NewName, service.contactRepository.get(ID).phoneNumber(), service.contactRepository.get(ID).address())))
		.get()
		.extracting(p -> p.lastName())
		.isEqualTo(NewName);
		//System.out.println(service.contactRepository.get(ID));
	}
	
	// Test Update Phone number
	@ParameterizedTest
	@CsvSource({"100001,8675309909", "100003,5555557942", "100002,0000000000"})
	public void update_UpdatePhoneNumber(String ID, String NewNumber) {
		service.contactRepository.get(ID).lastName();
		assertThat(service.update(new Contact(ID, service.contactRepository.get(ID).firstName(), service.contactRepository.get(ID).lastName(), NewNumber, service.contactRepository.get(ID).address())))
		.get()
		.extracting(p -> p.phoneNumber())
		.isEqualTo(NewNumber);
		//System.out.println(service.contactRepository.get(ID));
	}
	
	// Test Update Address
	@ParameterizedTest
	@CsvSource({"100001,Updated Address", "100003,Updated Address2", "100002,Updated Address3"})
	public void update_UpdateAddress(String ID, String NewAddress) {
		service.contactRepository.get(ID).lastName();
		assertThat(service.update(new Contact(ID, service.contactRepository.get(ID).firstName(), service.contactRepository.get(ID).lastName(), service.contactRepository.get(ID).phoneNumber(), NewAddress)))
		.get()
		.extracting(p -> p.address())
		.isEqualTo(NewAddress);
		//System.out.println(service.contactRepository.get(ID));
	}
	
	// Test Update ID (ID cannot be update able)
	@Test
	public void update_IDDoesNotExist() {
		assertThrows(IllegalArgumentException.class, 
				() -> service.update(new Contact("12345686", "NEW", "Contact", "8675309090", "Wrong Addrss")));		
	}
	
	@Test
	// Test Update if Invalid ID is Given
	public void update_InvalidID() {
		assertThrows(IllegalArgumentException.class, 
				() -> service.update(new Contact("12345686", "NEW", "Contact", "8675309090", "Wrong Addrss")));		
	}
		
	// Test Update with Null ID
	@Test
	public void update_IDisNull() {
		assertThrows(IllegalArgumentException.class, 
				() -> service.update(new Contact(null, "NEW", "Contact", "8675309090", "Wrong Addrss")));
		
	}

}
