package com.osc.oscms.course.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 班级响应DTO
 */
@Data
public class ClassResponse {
    private Long id;
    private Long courseId;
    private String name;
    private String description;
    private Long instructorId;
    private Integer maxStudents;
    private Integer currentStudents;
    private String status;
    private String semester;
    private Integer year;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



