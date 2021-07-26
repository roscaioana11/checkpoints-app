package ro.fasttrackit.checkpoints.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "checkpoints")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckpointEntity {
    @Id
    private String id;
    private String description;
    private String courseId;
    private long order;
}
