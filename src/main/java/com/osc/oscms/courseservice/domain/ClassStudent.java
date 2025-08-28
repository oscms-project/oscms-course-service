package com.osc.oscms.courseservice.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("osc_class_student")
public class ClassStudent {
    @TableId(value = "class_id", type = IdType.INPUT)
    private Long classId;

    @TableField("student_id")
    private String studentId;

    private LocalDateTime enrolledAt;
}
