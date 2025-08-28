package com.osc.oscms.course.service;

import com.osc.oscms.course.dto.*;

import java.util.List;

/**
 * 章节服务接口
 */
public interface ChapterService {

    /**
     * 创建章节
     */
    ChapterResponse createChapter(ChapterCreateRequest request);

    /**
     * 更新章节
     */
    ChapterResponse updateChapter(Long id, ChapterUpdateRequest request);

    /**
     * 删除章节
     */
    void deleteChapter(Long id);

    /**
     * 根据ID获取章节
     */
    ChapterResponse getChapterById(Long id);

    /**
     * 根据课程ID获取章节列表
     */
    List<ChapterResponse> getChaptersByCourseId(Long courseId);

    /**
     * 根据课程ID和状态获取章节列表
     */
    List<ChapterResponse> getChaptersByCourseIdAndStatus(Long courseId, String status);

    /**
     * 重新排序章节
     */
    void reorderChapters(Long courseId, List<Long> chapterIds);
}



