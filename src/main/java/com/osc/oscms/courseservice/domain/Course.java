package com.osc.oscms.courseservice.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("osc_course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String outline;
    private String objectives;
    private String assessment;
    @TableField("teacher_id")
    private String teacherId;
    private Boolean completed;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private List<Chapter> chapters;
}
