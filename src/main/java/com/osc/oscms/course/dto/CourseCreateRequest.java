package com.osc.oscms.course.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

/**
 * 课程创建请求DTO
 */
@Data
public class CourseCreateRequest {
    @NotBlank(message = "课程名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "授课教师ID不能为空")
    private Long instructorId;

    private String instructorName;

    @NotNull(message = "学分不能为空")
    @Min(value = 1, message = "学分必须大于0")
    private Integer credits;

    private String status = "DRAFT"; // 默认为草稿状态
}
