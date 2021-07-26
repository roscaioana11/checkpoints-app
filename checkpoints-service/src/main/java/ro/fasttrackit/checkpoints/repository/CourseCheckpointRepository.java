package ro.fasttrackit.checkpoints.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.checkpoints.model.entity.CheckpointEntity;

import java.util.List;
import java.util.Optional;

public interface CourseCheckpointRepository extends MongoRepository<CheckpointEntity, String> {
    List<CheckpointEntity> findAllByCourseId(String courseId);

    Optional<CheckpointEntity> findByCourseIdAndId(String courseId, String checkpointId);

    Optional<CheckpointEntity> findFirstByCourseIdOrderByOrderDesc(String courseId);
}
