package com.osc.oscms.course.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 课程响应DTO
 */
@Data
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private Long instructorId;
    private String instructorName;
    private Integer credits;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



