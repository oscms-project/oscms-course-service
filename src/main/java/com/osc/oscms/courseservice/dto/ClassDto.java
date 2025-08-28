package com.osc.oscms.courseservice.dto;

import lombok.Data;

@Data
public class ClassDto {
    private Long id;
    private String name;
    private String code;
    private Long courseId;
}
