package com.osc.oscms.course.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 章节创建请求DTO
 */
@Data
public class ChapterCreateRequest {
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @NotBlank(message = "章节标题不能为空")
    private String title;

    private String description;

    private Integer orderNum;

    private String status = "DRAFT"; // 默认为草稿状态
}
