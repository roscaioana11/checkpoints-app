package ro.fasttrackit.checkpoints.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.checkpoints.model.domain.CheckpointWithFeedback;
import ro.fasttrackit.checkpoints.model.entity.CheckpointEntity;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;
import ro.fasttrackit.checkpoints.model.entity.FeedbackStatus;
import ro.fasttrackit.checkpoints.model.entity.StudentEntity;
import ro.fasttrackit.checkpoints.repository.CourseCheckpointRepository;
import ro.fasttrackit.checkpoints.repository.FeedbackRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CourseCheckpointService {
    private final CourseCheckpointRepository repository;
    private final FeedbackRepository feedbackRepository;

    public Collection<CheckpointWithFeedback> getAllForCourse(String courseId) {
        List<CheckpointEntity> checkpoints = repository.findAllByCourseId(courseId);
        Map<String, List<FeedbackEntity>> checkpointToFeedback = feedbackRepository.findByCheckpointIdIn(checkpoints.stream()
                .map(CheckpointEntity::getId)
                .collect(toList()))
                .stream()
                .collect(groupingBy(FeedbackEntity::getCheckpointId));

        return checkpoints.stream()
                .map(checkpoint -> CheckpointWithFeedback.builder()
                        .checkpoint(checkpoint)
                        .feedback(checkpointToFeedback.getOrDefault(checkpoint.getId(), List.of()))
                        .build())
                .collect(toList());
    }

    public Optional<CheckpointWithFeedback> getOne(String courseId, String checkpointId) {
        Optional<CheckpointEntity> checkpoint = repository.findByCourseIdAndId(courseId, checkpointId);
        if (checkpoint.isEmpty()) {
            return Optional.empty();
        }
        List<FeedbackEntity> feedback = feedbackRepository.findByCheckpointId(checkpointId);
        return Optional.of(CheckpointWithFeedback.builder()
                .checkpoint(checkpoint.get())
                .feedback(feedback)
                .build());
    }

    public CheckpointWithFeedback create(String courseId, String description, List<StudentEntity> courseStudents) {
        CheckpointEntity checkpoint = repository.save(CheckpointEntity.builder()
                .courseId(courseId)
                .description(description)
                .build());
        List<FeedbackEntity> feedback = courseStudents.stream()
                .map(student -> createFeedbackForCheckpoint(checkpoint, student))
                .collect(toList());
        return CheckpointWithFeedback.builder()
                .checkpoint(checkpoint)
                .feedback(feedback)
                .build();
    }

    private FeedbackEntity createFeedbackForCheckpoint(CheckpointEntity checkpoint, StudentEntity student) {
        return feedbackRepository.save(FeedbackEntity.builder()
                .checkpointId(checkpoint.getId())
                .status(FeedbackStatus.IN_PROGRESS)
                .studentId(student.getId())
                .studentName(student.getName())
                .build());
    }

    public Optional<CheckpointEntity> getLastCheckpointForCourse(String courseId) {
        return repository.findFirstByCourseIdOrderByOrderDesc(courseId);
    }
}
