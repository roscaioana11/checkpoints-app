package ro.fasttrackit.checkpoints.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckpointsAppClientTest {
    private CheckpointsAppClient client;

    @BeforeEach
    void setup() {
        client = new CheckpointsAppClient("http://localhost:8080/");
    }

    @Test
    void getAllCourses() {
        System.out.println(client.courses()
                .get());
    }

    @Test
    void getAllStudentsForCourse() {
        String courseId = getFirstCourseId();
        System.out.println(client.courses(courseId)
                .students()
                .get());
    }

    @Test
    void enrollStudent() {
        String courseId = getFirstCourseId();
        client.courses(courseId)
                .students()
                .enroll("Stefan");
    }

    private String getFirstCourseId() {
        return client.courses()
                .get()
                .get(0)
                .getId();
    }

    @Test
    void getCheckpoints() {
        client.courses(getFirstCourseId())
                .checkpoints()
                .get();
    }

    @Test
    void addCheckpoint() {
        client.courses(getFirstCourseId())
                .checkpoints()
                .create();
    }
}
