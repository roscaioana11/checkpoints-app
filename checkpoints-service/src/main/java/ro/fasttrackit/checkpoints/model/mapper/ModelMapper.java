package ro.fasttrackit.checkpoints.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

public interface ModelMapper<API, DB> {
    API toApi(DB source);

    DB toDb(API source);

    default List<API> toApi(Collection<DB> source) {
        return ofNullable(source)
                .map(coursesList -> coursesList.stream()
                        .map(this::toApi)
                        .collect(Collectors.toList()))
                .orElse(emptyList());
    }

    default List<DB> toDb(Collection<API> source) {
        return ofNullable(source)
                .map(coursesList -> coursesList.stream()
                        .map(this::toDb)
                        .collect(Collectors.toList()))
                .orElse(emptyList());
    }

}
