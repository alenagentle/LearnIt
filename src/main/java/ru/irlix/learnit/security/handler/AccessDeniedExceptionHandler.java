package ru.irlix.learnit.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.irlix.learnit.security.util.ErrorResponseBodyFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    private final ErrorResponseBodyFactory responseFactory;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.error(accessDeniedException.getMessage());
        String message = accessDeniedException.getMessage();
        int status = HttpServletResponse.SC_FORBIDDEN;
        String error = "Forbidden";
        responseFactory.createBody(request, response, message, status, error);
    }
}
