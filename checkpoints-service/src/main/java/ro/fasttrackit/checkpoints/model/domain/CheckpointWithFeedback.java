package ro.fasttrackit.checkpoints.model.domain;

import lombok.Builder;
import lombok.Value;
import ro.fasttrackit.checkpoints.model.entity.CheckpointEntity;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;

import java.util.List;

@Value
@Builder
public class CheckpointWithFeedback {
    CheckpointEntity checkpoint;
    List<FeedbackEntity> feedback;
}
