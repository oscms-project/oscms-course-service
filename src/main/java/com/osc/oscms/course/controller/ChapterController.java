package com.osc.oscms.course.controller;

import com.osc.oscms.course.dto.*;
import com.osc.oscms.course.service.ChapterService;
import com.osc.oscms.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 章节控制器
 */
@RestController
@RequestMapping("/api/chapters")
@Tag(name = "章节管理", description = "章节相关API")
@Validated
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @PostMapping
    @Operation(summary = "创建章节")
    public CommonResponse<ChapterResponse> createChapter(@Valid @RequestBody ChapterCreateRequest request) {
        ChapterResponse response = chapterService.createChapter(request);
        return CommonResponse.success(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新章节")
    public CommonResponse<ChapterResponse> updateChapter(@PathVariable Long id,
            @Valid @RequestBody ChapterUpdateRequest request) {
        ChapterResponse response = chapterService.updateChapter(id, request);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除章节")
    public CommonResponse<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return CommonResponse.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取章节")
    public CommonResponse<ChapterResponse> getChapterById(@PathVariable Long id) {
        ChapterResponse response = chapterService.getChapterById(id);
        return CommonResponse.success(response);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "根据课程ID获取章节列表")
    public CommonResponse<List<ChapterResponse>> getChaptersByCourseId(@PathVariable Long courseId) {
        List<ChapterResponse> response = chapterService.getChaptersByCourseId(courseId);
        return CommonResponse.success(response);
    }

    @GetMapping("/course/{courseId}/status/{status}")
    @Operation(summary = "根据课程ID和状态获取章节列表")
    public CommonResponse<List<ChapterResponse>> getChaptersByCourseIdAndStatus(@PathVariable Long courseId,
            @PathVariable String status) {
        List<ChapterResponse> response = chapterService.getChaptersByCourseIdAndStatus(courseId, status);
        return CommonResponse.success(response);
    }

    @PutMapping("/course/{courseId}/reorder")
    @Operation(summary = "重新排序章节")
    public CommonResponse<Void> reorderChapters(@PathVariable Long courseId,
            @RequestBody List<Long> chapterIds) {
        chapterService.reorderChapters(courseId, chapterIds);
        return CommonResponse.success();
    }
}
