package ro.fasttrackit.checkpoints.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.checkpoints.client.model.Course;
import ro.fasttrackit.checkpoints.exception.ResourceNotFoundException;
import ro.fasttrackit.checkpoints.model.mapper.CourseMapper;
import ro.fasttrackit.checkpoints.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CourseController {
    private final CourseService service;
    private final CourseMapper mapper;

    @GetMapping
    List<Course> getAll() {
        return mapper.toApi(service.getAll());
    }

    @GetMapping("{courseId}")
    Course getCourse(@PathVariable String courseId) {
        return service.getById(courseId)
                .map(mapper::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find course with id " + courseId));
    }

    @PostMapping
    Course createCourse(@RequestBody Course newCourse) {
        return mapper.toApi(
                service.createCourse(mapper.toDb(newCourse)));
    }

    @DeleteMapping("{courseId}")
    Course deleteCourse(@PathVariable String courseId) {
        return service.deleteCourse(courseId)
                .map(mapper::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find course with id " + courseId));
    }

}
