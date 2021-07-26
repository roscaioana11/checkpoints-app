package ro.fasttrackit.checkpoints.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.checkpoints.model.entity.StudentEntity;

import java.util.List;

public interface StudentRepository extends MongoRepository<StudentEntity, String> {
    List<StudentEntity> findAllByCourseId(String courseId);
}
