/*
 * @author		Jonathan Kleven
 * @version		Module 3-2 Milestone Contact Service
 * @Date		1/22/2023
 */

package org.snhu.cs320.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

//https://clintonbush.notion.site/Module-3-Milestone-1-Contact-Service-c4421c03867a414fba5134be3e228bfc
//Option 2
public record Contact(
		
	@NotBlank(message = "ID is a required field")
	@Size(max = 10, message = "ID cannot be longer than {max} characters")
	String id,
		
	@NotBlank(message = "First Name is a required field")
	@Size(max = 10, message = "First Name cannot be longer than {max} characters")
	String firstName,
		
	@NotBlank(message = "Last Name is a required field")
	@Size(max = 10, message = "Last Name cannot be longer than {max} characters")
	String lastName,
		
	@NotBlank(message = "Phone Number is a required field")
	@Pattern(regexp = "[0-9]{10}", message = "Phone Number must be exactly 10 digits")
	String phoneNumber,
		
	@NotBlank(message = "Address is a required field")
	@Size(max = 30, message = "Address cannot be longer than {max} characters")
	String address
){
	//TODO contact object shall have a required unique contact ID string
}
