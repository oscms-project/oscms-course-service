package com.osc.oscms.course.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 班级学生关联实体类
 */
@Data
@TableName("class_students")
public class ClassStudent {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("class_id")
    private Long classId;

    @TableField("student_id")
    private Long studentId;

    @TableField("student_name")
    private String studentName;

    @TableField("student_email")
    private String studentEmail;

    @TableField("status")
    private String status; // ACTIVE, DROPPED, COMPLETED

    @TableField("enrollment_date")
    private LocalDateTime enrollmentDate;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}



