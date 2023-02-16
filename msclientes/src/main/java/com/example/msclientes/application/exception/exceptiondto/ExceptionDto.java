package com.example.msclientes.application.exception.exceptiondto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDto {
    private Integer status;

    private String message;

    private String path;
}
