package com.osc.oscms.course.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 章节响应DTO
 */
@Data
public class ChapterResponse {
    private Long id;
    private Long courseId;
    private String title;
    private String description;
    private Integer orderNum;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



