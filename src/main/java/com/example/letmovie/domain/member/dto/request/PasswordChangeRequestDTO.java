package com.example.letmovie.domain.member.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PasswordChangeRequestDTO {
    private String currentPassword;
    private String confirmPassword;
    private String newPassword;
}
