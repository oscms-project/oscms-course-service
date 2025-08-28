package com.osc.oscms.courseservice.dto;

import java.util.List;
import lombok.Data;

@Data
public class CourseDto {
    private Long id;

    private String name;

    private String code;

    private String outline;

    private String objectives;

    private String assessment;

    private String teacherId;
    private String teacherName;  // <--- 新增：教师的用户名或姓名

    private List<ChapterCreateDto> chapters;

    private Boolean completed;
}
