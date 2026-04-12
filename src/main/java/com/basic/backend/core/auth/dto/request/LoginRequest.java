package com.basic.backend.core.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "userId는 필수입니다.")
    private String userId;

    @NotBlank(message = "password는 필수입니다.")
    private String password;
}