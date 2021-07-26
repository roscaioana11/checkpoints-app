package ro.fasttrackit.checkpoints.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;
import ro.fasttrackit.checkpoints.repository.FeedbackRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository repository;

    public Optional<FeedbackEntity> getOne(String feedbackId) {
        return repository.findById(feedbackId);
    }

    public Optional<FeedbackEntity> getFeedbackForStudent(String checkpointId, String studentId) {
        return repository.findFirstByCheckpointIdAndStudentId(checkpointId, studentId);
    }

    public FeedbackEntity create(FeedbackEntity feedback) {
        feedback.setId(null);
        return repository.save(feedback);
    }

    public Optional<FeedbackEntity> patchFeedback(String feedbackId, FeedbackEntity feedback) {
        return getOne(feedbackId)
                .map(dbFeedback -> {
                    dbFeedback.setStatus(feedback.getStatus());
                    return dbFeedback;
                }).map(repository::save);
    }
}
