package com.osc.oscms.course.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 班级实体类
 */
@Data
@TableName("classes")
public class ClassEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("course_id")
    private Long courseId;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("instructor_id")
    private Long instructorId;

    @TableField("max_students")
    private Integer maxStudents;

    @TableField("current_students")
    private Integer currentStudents;

    @TableField("status")
    private String status; // ACTIVE, INACTIVE, FULL

    @TableField("semester")
    private String semester;

    @TableField("year")
    private Integer year;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}



