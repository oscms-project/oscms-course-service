package com.osc.oscms.courseservice.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("osc_class")
public class ClassEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    @TableField("course_id")
    private Long courseId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
