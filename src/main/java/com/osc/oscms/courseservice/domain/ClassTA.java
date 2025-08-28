package com.osc.oscms.courseservice.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("osc_class_ta")
public class ClassTA {
    @TableId(value = "class_id", type = IdType.INPUT)
    private Long classId;

    @TableField("ta_id")
    private String taId;

    private LocalDateTime assignedAt;
}
