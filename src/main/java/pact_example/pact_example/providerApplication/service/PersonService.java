package pact_example.pact_example.providerApplication.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pact_example.pact_example.providerApplication.dao.PersonDao;
import pact_example.pact_example.providerApplication.model.Person;

@Service
public class PersonService {
	private final PersonDao personDao; //dependency injection, dont use FakePersonDataAccessService (concrete class)
	
	@Autowired
	public PersonService(@Qualifier("fakeDao") PersonDao personDao) {
		this.personDao=personDao;
	}
	
	public int addPerson(Person person) {
		return this.personDao.insertPerson(person);
	}
	
	public List<Person> getAllPeople(){
		return this.personDao.selectAllPeople();
	}
	
	public Optional<Person> getPersonById(UUID id){
		return this.personDao.selectPersonById(id);
	}
	
	public int deletePerson(UUID id) {
		return this.personDao.deletePersonById(id);
	}
	
	public int updatePerson(UUID id, Person newPerson) {
		return this.personDao.updatePersonById(id, newPerson);
	}

}
