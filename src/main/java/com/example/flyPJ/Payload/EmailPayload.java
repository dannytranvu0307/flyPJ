package com.example.flyPJ.Payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EmailPayload {
    @NotBlank
    @Size(min = 16, max = 128)
    private String email;
}
