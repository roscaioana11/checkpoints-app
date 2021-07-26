package ro.fasttrackit.checkpoints.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.checkpoints.client.model.Checkpoint;
import ro.fasttrackit.checkpoints.client.model.Course;
import ro.fasttrackit.checkpoints.client.model.Student;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class CheckpointsAppClient {
    private final String baseUrl;

    public CheckpointsAppClient(String url) {
        this.baseUrl = url;
    }

    public CoursesClient courses() {
        return new CoursesClient(baseUrl);
    }

    public SingleCourseClient courses(String courseId) {
        return new SingleCourseClient(baseUrl, courseId);
    }

    public class SingleCourseClient {
        private final String url;

        public SingleCourseClient(String url, String courseId) {
            this.url = UriComponentsBuilder.fromHttpUrl(url)
                    .path("/courses/")
                    .path(courseId)
                    .toUriString();
        }

        public Course get() {
            return newRestTemplate().getForObject(this.url, Course.class);
        }

        public CourseStudentsClient students() {
            return new CourseStudentsClient(url);
        }

        public CourseCheckpointsClient checkpoints() {
            return new CourseCheckpointsClient(url);
        }

        public class CourseStudentsClient {
            private final String url;

            public CourseStudentsClient(String url) {
                this.url = UriComponentsBuilder.fromHttpUrl(url)
                        .path("/students")
                        .toUriString();
            }

            public List<Student> get() {
                return newRestTemplate().exchange(url,
                        GET,
                        new HttpEntity<>(null),
                        new ParameterizedTypeReference<List<Student>>() {
                        }).getBody();
            }

            public Student enroll(String name) {
                Student newStudent = Student.builder().name(name).build();
                return newRestTemplate().postForObject(this.url, newStudent, Student.class);
            }

        }

        public class CourseCheckpointsClient {
            private final String url;

            public CourseCheckpointsClient(String url) {
                this.url = UriComponentsBuilder.fromHttpUrl(url)
                        .path("/checkpoints")
                        .toUriString();
            }

            public List<Checkpoint> get() {
                return newRestTemplate().exchange(url,
                        GET,
                        new HttpEntity<>(null),
                        new ParameterizedTypeReference<List<Checkpoint>>() {
                        }).getBody();

            }

            public Checkpoint create() {
                    return newRestTemplate().postForObject(url, Checkpoint.builder()
                    .description("new thing")
                    .build(), Checkpoint.class);
            }
        }
    }

    public class CoursesClient {
        private final String url;

        public CoursesClient(String baseUrl) {
            url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .path("courses")
                    .toUriString();
        }

        public List<Course> get() {
            return newRestTemplate().exchange(url,
                    GET,
                    new HttpEntity<>(null),
                    new ParameterizedTypeReference<List<Course>>() {
                    }).getBody();
        }
    }

    private RestTemplate newRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
