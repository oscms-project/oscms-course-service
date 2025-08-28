package com.osc.oscms.course.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 课程章节实体类
 */
@Data
@TableName("chapters")
public class Chapter {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("course_id")
    private Long courseId;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("order_num")
    private Integer orderNum;

    @TableField("status")
    private String status; // ACTIVE, INACTIVE, DRAFT

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}



