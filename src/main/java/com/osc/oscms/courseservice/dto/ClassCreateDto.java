package com.osc.oscms.courseservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 班级创建DTO
 */
@Data
public class ClassCreateDto {
    
    @NotBlank(message = "班级名称不能为空")
    private String name;
    
    @NotBlank(message = "班级代码不能为空")
    private String code;
    
    @NotNull(message = "课程ID不能为空")
    private Long courseId;
}