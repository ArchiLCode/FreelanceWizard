package ru.solonchev.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.solonchev.backend.dto.task.response.TaskResponseDto;
import ru.solonchev.backend.persistence.entity.Task;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskResponseDtoMapper {
    TaskResponseDto map(Task task);

    default String map(LocalDateTime time) {
        return time != null ? time.toString() : null;
    }
}
