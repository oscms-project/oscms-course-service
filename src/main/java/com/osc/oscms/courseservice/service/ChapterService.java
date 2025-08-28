package com.osc.oscms.courseservice.service;

import com.osc.oscms.courseservice.dto.ChapterCreateDto;
import com.osc.oscms.courseservice.domain.Chapter;

import java.util.List;

/**
 * 章节服务接口
 */
public interface ChapterService {
    
    /**
     * 创建章节
     */
    Chapter createChapter(ChapterCreateDto chapterCreateDto);
    
    /**
     * 更新章节
     */
    Chapter updateChapter(Long chapterId, Chapter chapter);
    
    /**
     * 删除章节
     */
    void deleteChapter(Long chapterId);
    
    /**
     * 根据ID获取章节
     */
    Chapter getChapterById(Long chapterId);
    
    /**
     * 根据课程ID获取章节列表
     */
    List<Chapter> getChaptersByCourseId(Long courseId);
    
    /**
     * 调整章节顺序
     */
    void reorderChapters(Long courseId, List<Long> chapterIds);
}
