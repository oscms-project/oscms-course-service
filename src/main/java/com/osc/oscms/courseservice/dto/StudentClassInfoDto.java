package com.osc.oscms.courseservice.dto;

import lombok.Data;

@Data
public class StudentClassInfoDto {
    private Long classId;
    private String className;
    private Long courseId;
    private String courseName;
}
