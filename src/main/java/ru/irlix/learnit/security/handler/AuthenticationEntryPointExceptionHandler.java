package ru.irlix.learnit.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.irlix.learnit.security.util.ResponseBodyCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error(authException.getMessage());
        String message = authException.getMessage();
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String error = "Unauthorized";
        ResponseBodyCreator.createBody(request, response, message, status, error);
    }
}
