package pact_example.pact_example.providerApplication.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import pact_example.pact_example.providerApplication.model.Person;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

	@Override
	public int insertPerson(UUID id, Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> selectAllPeople() {
		// TODO Auto-generated method stub
		return List.of(new Person(UUID.randomUUID(),"FROM POSTGRES DB"));
	}

	@Override
	public int deletePersonById(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		// TODO Auto-generated method stub
		return 0;
	}

}
