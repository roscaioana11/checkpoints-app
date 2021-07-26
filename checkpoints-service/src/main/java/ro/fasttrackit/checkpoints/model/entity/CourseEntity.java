package ro.fasttrackit.checkpoints.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
@Data
@Builder
public class CourseEntity {
    @Id
    private String id;
    private String name;
}
