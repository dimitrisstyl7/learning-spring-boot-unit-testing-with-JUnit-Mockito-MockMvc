package dimstyl.testingspringbootmvcwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GradeRepository<T, ID> extends CrudRepository<T, ID> {

    List<T> findAllByStudentId(int studentId);

    void deleteAllByStudentId(int studentId);

}
