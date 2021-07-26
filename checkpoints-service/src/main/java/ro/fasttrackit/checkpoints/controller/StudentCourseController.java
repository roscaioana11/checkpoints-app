package ro.fasttrackit.checkpoints.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.checkpoints.client.model.Checkpoint;
import ro.fasttrackit.checkpoints.client.model.Student;
import ro.fasttrackit.checkpoints.exception.ResourceNotFoundException;
import ro.fasttrackit.checkpoints.model.mapper.CheckpointMapper;
import ro.fasttrackit.checkpoints.model.mapper.StudentMapper;
import ro.fasttrackit.checkpoints.service.StudentCourseService;

import java.util.List;

@RestController
@RequestMapping("courses/{courseId}/students")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class StudentCourseController {
    private final StudentCourseService service;
    private final StudentMapper mapper;
    private final CheckpointMapper checkpointMapper;

    @GetMapping
    List<Student> getAll(@PathVariable String courseId) {
        return mapper.toApi(service.getStudentsForCourse(courseId));
    }

    @PostMapping
    Student assignStudentToCourse(@PathVariable String courseId,
                                  @RequestBody Student student) {
        return mapper.toApi(service.assignStudentToCourse(mapper.toDb(student), courseId));
    }

    @GetMapping("{studentId}/checkpoint")
    Checkpoint getCheckpointForStudent(@PathVariable String courseId, @PathVariable String studentId) {
        return checkpointMapper.toApi(service.getCheckpointForStudent(courseId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not get your checkpoint " + studentId)));
    }
}
