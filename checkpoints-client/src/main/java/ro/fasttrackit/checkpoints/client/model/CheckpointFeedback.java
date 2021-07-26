package ro.fasttrackit.checkpoints.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckpointFeedback {
    private String id;
    private String studentName;
    private FeedbackStatus status;
}
