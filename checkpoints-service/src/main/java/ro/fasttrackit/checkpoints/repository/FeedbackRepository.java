package ro.fasttrackit.checkpoints.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<FeedbackEntity, String> {
    List<FeedbackEntity> findByCheckpointIdIn(List<String> checkpointIds);

    List<FeedbackEntity> findByCheckpointId(String checkpointId);

    Optional<FeedbackEntity> findFirstByCheckpointIdAndStudentId(String checkpointId, String studentId);
}
