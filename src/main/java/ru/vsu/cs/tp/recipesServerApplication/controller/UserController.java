package ru.vsu.cs.tp.recipesServerApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.tp.recipesServerApplication.dto.response.email.EmailResponse;
import ru.vsu.cs.tp.recipesServerApplication.service.EmailService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "The functions of the user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final EmailService emailService;

    @GetMapping("/email")
    @Operation(description = "Getting the user's email")
    public ResponseEntity<EmailResponse> getUserEmail(
            @RequestHeader(name="token") String jwt
    ) {
        EmailResponse response = emailService.getEmail(jwt);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
