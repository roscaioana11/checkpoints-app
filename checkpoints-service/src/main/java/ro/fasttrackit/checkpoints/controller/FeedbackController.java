package ro.fasttrackit.checkpoints.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.checkpoints.client.model.CheckpointFeedback;
import ro.fasttrackit.checkpoints.exception.ResourceNotFoundException;
import ro.fasttrackit.checkpoints.model.mapper.FeedbackMapper;
import ro.fasttrackit.checkpoints.service.FeedbackService;

@RestController
@RequestMapping("feedbacks/{feedbackId}")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class FeedbackController {
    private final FeedbackService service;
    private final FeedbackMapper mapper;

    @GetMapping
    CheckpointFeedback getOne(@PathVariable String feedbackId) {
        return mapper.toApi(service.getOne(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find feedback with id " + feedbackId)));
    }

    @PatchMapping
    CheckpointFeedback patchFeedback(@PathVariable String feedbackId, @RequestBody CheckpointFeedback feedback) {
        return mapper.toApi(service.patchFeedback(feedbackId, mapper.toDb(feedback))
                .orElseThrow(() -> new ResourceNotFoundException("Could not find feedback with id " + feedbackId)));
    }
}
