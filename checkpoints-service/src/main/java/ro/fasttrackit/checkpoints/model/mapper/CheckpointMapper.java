package ro.fasttrackit.checkpoints.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.checkpoints.client.model.Checkpoint;
import ro.fasttrackit.checkpoints.model.domain.CheckpointWithFeedback;
import ro.fasttrackit.checkpoints.model.entity.CheckpointEntity;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;

import java.util.List;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class CheckpointMapper implements ModelMapper<Checkpoint, CheckpointWithFeedback> {
    private final FeedbackMapper feedbackMapper;

    @Override
    public Checkpoint toApi(CheckpointWithFeedback source) {
        CheckpointEntity checkpoint = ofNullable(source.getCheckpoint())
                .orElse(CheckpointEntity.builder().build());
        List<FeedbackEntity> feedbackEntities = ofNullable(source.getFeedback())
                .orElse(List.of());
        return Checkpoint.builder()
                .id(checkpoint.getId())
                .description(checkpoint.getDescription())
                .feedback(feedbackMapper.toApi(feedbackEntities))
                .build();
    }

    @Override
    public CheckpointWithFeedback toDb(Checkpoint source) {
        return CheckpointWithFeedback.builder()
                .checkpoint(CheckpointEntity.builder()
                        .id(source.getId())
                        .description(source.getDescription())
                        .build())
                .feedback(feedbackMapper.toDb(source.getFeedback()))
                .build();
    }

}
