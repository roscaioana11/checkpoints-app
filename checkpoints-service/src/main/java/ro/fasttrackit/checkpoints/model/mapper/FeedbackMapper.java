package ro.fasttrackit.checkpoints.model.mapper;

import org.springframework.stereotype.Component;
import ro.fasttrackit.checkpoints.client.model.CheckpointFeedback;
import ro.fasttrackit.checkpoints.client.model.FeedbackStatus;
import ro.fasttrackit.checkpoints.model.entity.FeedbackEntity;

import static ro.fasttrackit.checkpoints.model.entity.FeedbackStatus.*;

@Component
public class FeedbackMapper implements ModelMapper<CheckpointFeedback, FeedbackEntity> {
    @Override
    public CheckpointFeedback toApi(FeedbackEntity source) {
        return CheckpointFeedback.builder()
                .id(source.getId())
                .status(statusToApi(source.getStatus()))
                .studentName(source.getStudentName())
                .build();
    }

    @Override
    public FeedbackEntity toDb(CheckpointFeedback source) {
        return FeedbackEntity.builder()
                .id(source.getId())
                .status(statusToDb(source.getStatus()))
                .studentName(source.getStudentName())
                .build();
    }

    private FeedbackStatus statusToApi(ro.fasttrackit.checkpoints.model.entity.FeedbackStatus status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case IN_PROGRESS:
                return FeedbackStatus.IN_PROGRESS;
            case DONE:
                return FeedbackStatus.DONE;
            case HELP:
                return FeedbackStatus.HELP;
            default:
                return FeedbackStatus.IN_PROGRESS;
        }
    }

    private ro.fasttrackit.checkpoints.model.entity.FeedbackStatus statusToDb(FeedbackStatus status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case IN_PROGRESS:
                return IN_PROGRESS;
            case DONE:
                return DONE;
            case HELP:
                return HELP;
            default:
                return IN_PROGRESS;
        }
    }

}
