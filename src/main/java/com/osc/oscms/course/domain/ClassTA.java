package com.osc.oscms.course.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 班级助教关联实体类
 */
@Data
@TableName("class_tas")
public class ClassTA {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("class_id")
    private Long classId;

    @TableField("ta_id")
    private Long taId;

    @TableField("ta_name")
    private String taName;

    @TableField("ta_email")
    private String taEmail;

    @TableField("status")
    private String status; // ACTIVE, INACTIVE

    @TableField("assigned_date")
    private LocalDateTime assignedDate;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}



