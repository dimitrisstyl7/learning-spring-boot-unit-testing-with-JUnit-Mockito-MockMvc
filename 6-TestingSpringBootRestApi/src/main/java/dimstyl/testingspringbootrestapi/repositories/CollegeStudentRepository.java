package dimstyl.testingspringbootrestapi.repositories;

import dimstyl.testingspringbootrestapi.models.CollegeStudent;
import org.springframework.data.repository.CrudRepository;

public interface CollegeStudentRepository extends CrudRepository<CollegeStudent, Integer> {

    CollegeStudent findByEmailAddress(String emailAddress);

    void deleteAll();

}
