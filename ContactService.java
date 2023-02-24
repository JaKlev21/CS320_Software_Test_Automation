/*
 * @author		Jonathan Kleven
 * @version		Module 3-2 Milestone Contact Service
 * @Date		1/22/2023
 */

package org.snhu.cs320.services;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.snhu.cs320.entities.Contact;
import org.snhu.cs320.validations.EntityValidator;

//https://clintonbush.notion.site/Module-3-Milestone-1-Contact-Service-c4421c03867a414fba5134be3e228bfc
//Option 2
public class ContactService {
	
	public static ContactService INSTANCE;
	
	final Map<String, Contact> contactRepository;
	final EntityValidator validator;
	
	private ContactService() {
		contactRepository = new ConcurrentHashMap<>();
		this.validator = new EntityValidator();
	}
	
	public static synchronized ContactService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ContactService();
		}
		return INSTANCE;
	}
	
	public Contact create(final Contact contact) {
		Objects.requireNonNull(contact);
		
		return validator.validateAndDoOrThrow(
				contact,
				p -> {
					if (contactRepository.containsKey(contact.id())) {
						throw new IllegalArgumentException(String.format("A contact with ID [%s] already exists.", contact.id()));
					}
					return contactRepository.put(contact.id(), contact);
				}
		);
		
	}

	public Optional<Contact> deleteById(final String id) {
		checkId(id);
		return Optional.ofNullable(contactRepository.remove(id));
	}
	
	public Optional<Contact> findById(final String id) {
		checkId(id);
		return Optional.ofNullable(contactRepository.get(id));
	}
		
	//  Update ID (ID cannot be update able
	public Optional<Contact> update(final Contact contact) {
		
		//System.out.println(contact);
		checkId(contact.id());
		if (contactRepository.containsKey(contact.id())) {
			contactRepository.put(contact.id(), contact);
		}
		else {
			throw new IllegalArgumentException("ID Does Not Exits");
		}
		
		return Optional.ofNullable(contact);
	}
	
	private static void checkId(final String id) {
		if(id == null || id.trim().length() < 1) {
			throw new IllegalArgumentException("ID is a required argument");
		}
		
	}
		
}
