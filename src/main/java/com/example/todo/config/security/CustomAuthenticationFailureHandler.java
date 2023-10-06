package com.example.todo.config.security;

import com.example.todo.model.dto.ErrorResponse;
import com.example.todo.model.enums.UserError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFailureHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        UserError userError = UserError.getErrorByAuthenticationEx(exception);

        printErrorMessage(response, userError);
    }

    private void printErrorMessage(HttpServletResponse response, @NonNull UserError userError) throws IOException {
        int statusCode;
        if (userError.getHttpStatus() != null) {
            statusCode = userError.getHttpStatus().value();
        } else {
            statusCode = HttpStatus.UNAUTHORIZED.value();
        }

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.of(userError);

        String errorJson = objectMapper.writeValueAsString(errorResponse);
        PrintWriter writer = response.getWriter();
        writer.println(errorJson);
    }
}
