package com.osc.oscms.courseservice.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseCreateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String code;

    private String outline;

    private String objectives;

    private String assessment;

    private List<ChapterCreateDto> chapters;  // 课程章节列表

    private Boolean completed;                // 课程是否结课
}
