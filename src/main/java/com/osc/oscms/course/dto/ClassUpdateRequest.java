package com.osc.oscms.course.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;

/**
 * 班级更新请求DTO
 */
@Data
public class ClassUpdateRequest {
    private String name;
    private String description;
    private Long instructorId;

    @Min(value = 1, message = "最大学生数必须大于0")
    private Integer maxStudents;

    private String status; // ACTIVE, INACTIVE, FULL
    private String semester;
    private Integer year;
}
