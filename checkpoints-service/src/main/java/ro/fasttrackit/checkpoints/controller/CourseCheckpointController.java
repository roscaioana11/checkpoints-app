package ro.fasttrackit.checkpoints.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.checkpoints.client.model.Checkpoint;
import ro.fasttrackit.checkpoints.exception.ResourceNotFoundException;
import ro.fasttrackit.checkpoints.model.mapper.CheckpointMapper;
import ro.fasttrackit.checkpoints.service.CourseCheckpointService;
import ro.fasttrackit.checkpoints.service.StudentCourseService;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/checkpoints")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CourseCheckpointController {
    private final CourseCheckpointService service;
    private final StudentCourseService studentService;
    private final CheckpointMapper mapper;

    @GetMapping
    List<Checkpoint> getAllForCourse(@PathVariable String courseId) {
        return mapper.toApi(service.getAllForCourse(courseId));
    }

    @GetMapping("{checkpointId}")
    Checkpoint getOne(@PathVariable String courseId, @PathVariable String checkpointId) {
        return mapper.toApi(service.getOne(courseId, checkpointId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find checkpoint " + checkpointId + " for course " + courseId)));
    }

    @PostMapping
    Checkpoint createCheckpoint(@PathVariable String courseId, @RequestBody Checkpoint checkpoint) {
        return mapper.toApi(service.create(courseId, checkpoint.getDescription(), studentService.getStudentsForCourse(courseId)));
    }

}
