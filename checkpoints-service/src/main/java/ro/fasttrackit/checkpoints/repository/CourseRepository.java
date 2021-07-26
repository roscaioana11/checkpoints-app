package ro.fasttrackit.checkpoints.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.checkpoints.model.entity.CourseEntity;

public interface CourseRepository extends MongoRepository<CourseEntity, String> {
}
