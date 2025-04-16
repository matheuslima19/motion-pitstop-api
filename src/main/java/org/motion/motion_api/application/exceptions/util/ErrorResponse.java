package org.motion.motion_api.application.exceptions.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;


@AllArgsConstructor @Getter @Setter @ToString
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private String path;
    private List<String> stackTrace;


    public ErrorResponse(HttpStatus status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
