package com.testShoopingCard.shopping_card.Security.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// custom class designed to handle unauthorized access attempts in a JWT-based authentication system. (User-Friendly Error Messages)
// JwtAuthEntryPoint is used to return a structured JSON response when a request is made without proper authentication
@Component
public class JwtAuthEntryPoint  implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
         response.setContentType(MediaType.APPLICATION_JSON_VALUE);     // response body will be in JSON format.
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);       // informing as authentication is required

         final Map<String, Object> body = new HashMap<>();
         body.put("status",HttpServletResponse.SC_UNAUTHORIZED);
         body.put("error","Unauthorized");
         body.put("message","Please login first and then access system");
         body.put("path",request.getServletPath());
// default message for  authException.getMessage()  == Full authentication is required to access this resource



         // Converting the Map to JSON
        // ObjectMapper is used to serialize the Map object (body) into a JSON format and write it to the response's output stream
         final ObjectMapper mapper = new ObjectMapper();
         mapper.writeValue(response.getOutputStream(),body);
    }
}
