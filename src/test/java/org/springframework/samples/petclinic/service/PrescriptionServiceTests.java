package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Prescription;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedUsernameException;
import org.springframework.stereotype.Service;

@DisplayName("Products Service Tests")
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PrescriptionServiceTests {
	
	@Autowired
	protected PrescriptionService prescriptionService;
	
	@Autowired
	protected VetService vetService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected OwnerService ownerService;
	
	
//	@BeforeEach
//	public void setInitialPrescription() {
//		Prescription p = new Prescription();
//		
//		User user = new User();
//		user.setUsername("carcru");
//		user.setPassword("1234568");
//		user.setEnabled(true);
//		
//		Pet pet = new Pet();
//		pet.setBirthDate(LocalDate.of(2020, 01, 01));
//		pet.setName("pepa");
//		pet.setType(null);
//		
//		Vet vet = new Vet();
//		vet.setFirstName("carlos");
//		vet.setLastName("cruz");
//		vet.setUser(user);
//		
//		p.setName("Titulo de Pueba");
//		p.setDateInicio(LocalDate.of(2020, 12, 12));
//		p.setDateFinal(LocalDate.of(2020, 12, 15));
//		p.setDescription("esta es una descripcion de prueba para los test");
//		p.setPet(pet);
//		p.setVet(vet);
//		
//	}
	
	@Test
	void shouldFindPrescriptionsByPetId(){
		Collection<Prescription> lista = this.prescriptionService.findPrescriptionsByPetId(1);
		Assertions.assertTrue(!lista.isEmpty());
	}
	
	
	@Test
	void shouldNoFindPrescriptionsByPetId(){
		Collection<Prescription> lista = this.prescriptionService.findPrescriptionsByPetId(1547268);
		Assertions.assertTrue(lista.isEmpty());
	}
	
	@Test
    public void shouldSavePrescription() throws DataAccessException, DuplicatedUsernameException, DuplicatedPetNameException{
	
		Collection<Prescription> prs = (Collection<Prescription>) this.prescriptionService.findPrescriptionsByPetId(1);
		int found = prs.size();
		
		Prescription p = new Prescription();
		
		User user = new User();
		user.setUsername("carcru");
		user.setPassword("1234568");
		user.setEnabled(true);
		
		Owner owner = new Owner();
		owner.setUser(user);
		owner.setAddress("hsha");
		owner.setCity("sevilla");
		owner.setFirstName("ajajaja");
		owner.setLastName("javi");
		owner.setTelephone("988755839");
		
		owner.setCreditCardNumber("5536848370023594");
		owner.setCvv("821");
		owner.setExpirationMonth(1);
		owner.setExpirationYear(2023);
		
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.of(2020, 01, 01));
		pet.setName("pepa");
		PetType cat = new PetType();
		cat.setName("cat");
		pet.setType(cat);
		pet.setOwner(owner);
		
		User user2 = new User();
		user.setUsername("carcrum");
		user.setPassword("12345628");
		user.setEnabled(true);
		
		Vet vet = new Vet();
		vet.setFirstName("carlos");
		vet.setLastName("cruz");
		vet.setUser(user2);
		
		Specialty sp = new Specialty();
		sp.setName("surgery");
		vet.addSpecialty(sp);
		
		p.setName("Titulo de Prueba");
		p.setDateInicio(LocalDate.of(2020, 12, 12));
		p.setDateFinal(LocalDate.of(2020, 12, 15));
		p.setDescription("esta es una descripcion de prueba para los test");
		p.setPet(pet);
		p.setVet(vet);
		
		this.prescriptionService.savePrescription(p);
		Assertions.assertTrue(p.getId()!=0);
	
		prs = (Collection<Prescription>) this.prescriptionService.findPrescriptionsByPetId(1);
		Assertions.assertTrue(prs.size() == found+1);
		
    }
	
	@Test
	void shouldNotSavePrescription() {
		Prescription prescription = new Prescription();
		User user1 = new User();
		Owner owner = new Owner();
		owner.setUser(user1);
		Pet pet = new Pet();
		pet.setOwner(owner);
		prescription.setPet(pet);
		User user2 = new User();
		Vet vet = new Vet();
		vet.setUser(user2);
		prescription.setVet(vet);
		Assertions.assertThrows(JpaSystemException.class, () -> this.prescriptionService.savePrescription(prescription));
	}

}