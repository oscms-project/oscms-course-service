package com.osc.oscms.course.dto;

import lombok.Data;

/**
 * 章节更新请求DTO
 */
@Data
public class ChapterUpdateRequest {
    private String title;
    private String description;
    private Integer orderNum;
    private String status; // ACTIVE, INACTIVE, DRAFT
}



