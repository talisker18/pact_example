package pact_example.pact_example.providerApplication.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import pact_example.pact_example.providerApplication.model.Person;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{
	private static List<Person> DB = new ArrayList<>();
	
	//add predefined person for pact testing
	static {
		UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		DB.add(new Person(uid, "Joel Henz"));
		UUID uid2 = UUID.fromString("7be8a0d2-7b33-11eb-9439-0242ac130002");
		DB.add(new Person(uid2, "Mani Henz"));
	}

	@Override
	public int insertPerson(UUID id, Person person) {
		// TODO Auto-generated method stub
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPeople() {
		// TODO Auto-generated method stub
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		return DB.stream()
				.filter(person -> person.getId().equals(id))
				.findFirst();
	}

	@Override
	public int deletePersonById(UUID id) {
		// TODO Auto-generated method stub
		Optional<Person> personMaybe = this.selectPersonById(id);
		if(personMaybe.isEmpty()) {
			return 0;
		}else {
			DB.remove(personMaybe.get());
			return 1;
		}
	}

	@Override
	public int updatePersonById(UUID id, Person updatedPersonObj) {
		// TODO Auto-generated method stub
		return this.selectPersonById(id).map(personToUpdate -> {
			int indexOfPersonToUpdate = DB.indexOf(personToUpdate);
			if(indexOfPersonToUpdate>=0) {
				DB.set(indexOfPersonToUpdate, updatedPersonObj);
				return 1;
			}else {
				return 0;
			}
		})
				.orElse(0);
	}

}
