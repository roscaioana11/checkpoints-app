package ro.fasttrackit.checkpoints.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.checkpoints.model.domain.CheckpointWithFeedback;
import ro.fasttrackit.checkpoints.model.entity.CheckpointEntity;
import ro.fasttrackit.checkpoints.model.entity.CourseEntity;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;
import ro.fasttrackit.checkpoints.model.entity.StudentEntity;
import ro.fasttrackit.checkpoints.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Service
@RequiredArgsConstructor
public class StudentCourseService {
    private final StudentRepository repository;
    private final CourseService courseService;
    private final CourseCheckpointService checkpointService;
    private final FeedbackService feedbackService;

    public List<StudentEntity> getStudentsForCourse(String courseId) {
        return repository.findAllByCourseId(courseId);
    }

    public StudentEntity assignStudentToCourse(StudentEntity student, String courseId) {
        CourseEntity course = courseService.getOrThrow(courseId);
        student.setId(null);
        student.setCourseId(course.getId());
        return repository.save(student);
    }

    public Optional<CheckpointWithFeedback> getCheckpointForStudent(String courseId, String studentId) {
        Optional<StudentEntity> studentEntity = findById(studentId);
        if (studentEntity.isEmpty()) {
            return empty();
        }
        Optional<CheckpointEntity> checkpoint = checkpointService.getLastCheckpointForCourse(courseId);
        if (checkpoint.isEmpty()) {
            return empty();
        }
        FeedbackEntity feedback = feedbackService.getFeedbackForStudent(checkpoint.get().getId(), studentId)
                .orElseGet(() -> feedbackService.create(FeedbackEntity.builder()
                        .studentId(studentId)
                        .checkpointId(checkpoint.get().getId())
                        .studentId(studentId)
                        .studentName(studentEntity.get().getName())
                        .build()));
        return Optional.of(
                CheckpointWithFeedback.builder()
                        .checkpoint(checkpoint.get())
                        .feedback(List.of(feedback))
                        .build());
    }

    private Optional<StudentEntity> findById(String studentId) {
        return repository.findById(studentId);
    }
}
