package pact_example.pact_example.providerApplication.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pact_example.pact_example.providerApplication.model.Person;

public interface PersonDao {
	int insertPerson(UUID id, Person person);
	
	default int insertPerson(Person person) {
		UUID id = UUID.randomUUID();
		return insertPerson(id,person);
	}
	
	Optional<Person> selectPersonById(UUID id);
	
	List<Person> selectAllPeople();
	
	int deletePersonById(UUID id);
	
	int updatePersonById(UUID id, Person person);

}
