package ru.vsu.cs.tp.recipesServerApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.email.EmailResponse;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JwtService jwtService;

    public EmailResponse getEmail(String jwt) {
        if (jwt == null)
            return null;

        String email = jwtService.extractUsername(jwt);
        if (email == null)
            return null;

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setEmail(email);

        return emailResponse;
    }
}
