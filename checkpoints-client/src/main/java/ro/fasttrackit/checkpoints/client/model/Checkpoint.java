package ro.fasttrackit.checkpoints.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Checkpoint {
    private String id;
    private String description;
    private List<CheckpointFeedback> feedback;
}
