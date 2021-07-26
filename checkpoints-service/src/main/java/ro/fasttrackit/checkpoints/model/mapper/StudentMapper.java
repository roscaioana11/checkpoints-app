package ro.fasttrackit.checkpoints.model.mapper;

import org.springframework.stereotype.Component;
import ro.fasttrackit.checkpoints.client.model.Student;
import ro.fasttrackit.checkpoints.model.entity.StudentEntity;

@Component
public class StudentMapper implements ModelMapper<Student, StudentEntity> {
    @Override
    public Student toApi(StudentEntity source) {
        return Student.builder()
                .id(source.getId())
                .name(source.getName())
                .courseId(source.getCourseId())
                .build();
    }

    @Override
    public StudentEntity toDb(Student source) {
        return StudentEntity.builder()
                .id(source.getId())
                .name(source.getName())
                .courseId(source.getCourseId())
                .build();
    }
}
