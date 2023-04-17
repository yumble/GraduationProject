package project.graduation.config.resultform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultException extends RuntimeException {
    private ResultResponseStatus status;
}
