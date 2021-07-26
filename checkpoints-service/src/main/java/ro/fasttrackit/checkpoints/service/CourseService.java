package ro.fasttrackit.checkpoints.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.checkpoints.exception.ResourceNotFoundException;
import ro.fasttrackit.checkpoints.model.entity.CourseEntity;
import ro.fasttrackit.checkpoints.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;

    public List<CourseEntity> getAll() {
        return repository.findAll();
    }

    public Optional<CourseEntity> getById(String courseId) {
        return repository.findById(courseId);
    }

    public CourseEntity createCourse(CourseEntity newCourse) {
        newCourse.setId(null);
        return repository.save(newCourse);
    }

    public Optional<CourseEntity> deleteCourse(String courseId) {
        Optional<CourseEntity> dbCourse = getById(courseId);
        dbCourse
                .ifPresent(repository::delete);
        return dbCourse;
    }

    public CourseEntity getOrThrow(String courseId) {
        return getById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find course with id " + courseId));
    }
}
