package com.osc.oscms.course.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;

/**
 * 课程更新请求DTO
 */
@Data
public class CourseUpdateRequest {
    private String name;

    private String description;

    private Long instructorId;

    private String instructorName;

    @Min(value = 1, message = "学分必须大于0")
    private Integer credits;

    private String status; // ACTIVE, INACTIVE, DRAFT
}
