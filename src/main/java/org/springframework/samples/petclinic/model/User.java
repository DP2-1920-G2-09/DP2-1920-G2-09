package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@NotBlank
	String username;
	@NotBlank
	String password;

	@NotBlank
	String email;

	boolean enabled;

}
