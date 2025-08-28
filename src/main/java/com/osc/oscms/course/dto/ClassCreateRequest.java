package com.osc.oscms.course.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

/**
 * 班级创建请求DTO
 */
@Data
public class ClassCreateRequest {
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @NotBlank(message = "班级名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "授课教师ID不能为空")
    private Long instructorId;

    @NotNull(message = "最大学生数不能为空")
    @Min(value = 1, message = "最大学生数必须大于0")
    private Integer maxStudents;

    private String status = "ACTIVE"; // 默认为活跃状态

    private String semester;

    private Integer year;
}
