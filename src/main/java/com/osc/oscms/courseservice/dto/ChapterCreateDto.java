package com.osc.oscms.courseservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChapterCreateDto {
    @NotBlank
    private String title;    // 章节标题

    @Min(0)
    private Integer order;   // 本章节在课程中的编号，从1开始
}
