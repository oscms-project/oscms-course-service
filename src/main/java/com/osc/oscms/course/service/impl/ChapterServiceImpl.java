package com.osc.oscms.course.service.impl;

import com.osc.oscms.course.dto.*;
import com.osc.oscms.course.domain.Chapter;
import com.osc.oscms.course.mapper.ChapterMapper;
import com.osc.oscms.course.service.ChapterService;
import com.osc.oscms.common.exception.BusinessException;
import com.osc.oscms.common.response.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 章节服务实现类
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public ChapterResponse createChapter(ChapterCreateRequest request) {
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(request, chapter);

        // 如果没有指定排序号，则自动设置为最大值+1
        if (chapter.getOrderNum() == null) {
            Integer maxOrderNum = chapterMapper.getMaxOrderNumByCourseId(request.getCourseId());
            chapter.setOrderNum(maxOrderNum + 1);
        }

        chapter.setCreatedAt(LocalDateTime.now());
        chapter.setUpdatedAt(LocalDateTime.now());

        int result = chapterMapper.insert(chapter);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "创建章节失败");
        }

        return convertToResponse(chapter);
    }

    @Override
    public ChapterResponse updateChapter(Long id, ChapterUpdateRequest request) {
        Chapter chapter = chapterMapper.selectById(id);
        if (chapter == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "章节不存在");
        }

        // 只更新非空字段
        if (request.getTitle() != null) {
            chapter.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            chapter.setDescription(request.getDescription());
        }
        if (request.getOrderNum() != null) {
            chapter.setOrderNum(request.getOrderNum());
        }
        if (request.getStatus() != null) {
            chapter.setStatus(request.getStatus());
        }
        chapter.setUpdatedAt(LocalDateTime.now());

        int result = chapterMapper.updateById(chapter);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "更新章节失败");
        }

        return convertToResponse(chapter);
    }

    @Override
    public void deleteChapter(Long id) {
        Chapter chapter = chapterMapper.selectById(id);
        if (chapter == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "章节不存在");
        }

        chapter.setStatus("DELETED");
        chapter.setUpdatedAt(LocalDateTime.now());

        int result = chapterMapper.updateById(chapter);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "删除章节失败");
        }
    }

    @Override
    public ChapterResponse getChapterById(Long id) {
        Chapter chapter = chapterMapper.selectById(id);
        if (chapter == null || "DELETED".equals(chapter.getStatus())) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "章节不存在");
        }

        return convertToResponse(chapter);
    }

    @Override
    public List<ChapterResponse> getChaptersByCourseId(Long courseId) {
        List<Chapter> chapters = chapterMapper.findByCourseId(courseId);
        return chapters.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChapterResponse> getChaptersByCourseIdAndStatus(Long courseId, String status) {
        List<Chapter> chapters = chapterMapper.findByCourseIdAndStatus(courseId, status);
        return chapters.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void reorderChapters(Long courseId, List<Long> chapterIds) {
        for (int i = 0; i < chapterIds.size(); i++) {
            Long chapterId = chapterIds.get(i);
            Chapter chapter = chapterMapper.selectById(chapterId);
            if (chapter != null && chapter.getCourseId().equals(courseId)) {
                chapter.setOrderNum(i + 1);
                chapter.setUpdatedAt(LocalDateTime.now());
                chapterMapper.updateById(chapter);
            }
        }
    }

    private ChapterResponse convertToResponse(Chapter chapter) {
        ChapterResponse response = new ChapterResponse();
        BeanUtils.copyProperties(chapter, response);
        return response;
    }
}



