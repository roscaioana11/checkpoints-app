package ro.fasttrackit.checkpoints.model.mapper;

import org.springframework.stereotype.Component;
import ro.fasttrackit.checkpoints.client.model.Course;
import ro.fasttrackit.checkpoints.model.entity.CourseEntity;

@Component
public class CourseMapper implements ModelMapper<Course, CourseEntity> {
    public Course toApi(CourseEntity course) {
        return Course.builder()
                .id(course.getId())
                .name(course.getName())
                .build();
    }

    public CourseEntity toDb(Course course) {
        return CourseEntity.builder()
                .id(course.getId())
                .name(course.getName())
                .build();

    }
}
