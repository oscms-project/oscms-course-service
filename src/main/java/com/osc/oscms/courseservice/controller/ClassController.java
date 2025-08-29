package com.osc.oscms.courseservice.controller;

import com.osc.oscms.common.response.ApiResponse;
import com.osc.oscms.common.dto.clazz.ClassCreateDto;
import com.osc.oscms.common.dto.clazz.ClassDto;
import com.osc.oscms.common.dto.clazz.StudentImportDto;
import com.osc.oscms.common.dto.clazz.TAImportDto;
import com.osc.oscms.common.dto.clazz.StudentAssignmentSummaryDto;

import com.osc.oscms.common.dto.common.ImportResultDto;
import com.osc.oscms.common.dto.user.UserResponse;
import com.osc.oscms.common.dto.material.MaterialDto;

import com.osc.oscms.courseservice.service.ClassService;
import com.osc.oscms.courseservice.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 班级管理控制器
 */
@RestController
@RequestMapping("/classes")
@Tag(name = "Class Management", description = "班级管理接口")
@RequiredArgsConstructor
@Validated
public class ClassController {

    private final ClassService classService;

    @PostMapping
    @Operation(summary = "创建班级", description = "创建新的班级")
    public ApiResponse<ClassDto> createClass(@Valid @RequestBody ClassCreateDto classCreateDto) {
        ClassDto result = classService.createClass(classCreateDto);
        return ApiResponse.ok(result);
    }

    @PutMapping("/{classId}")
    @Operation(summary = "更新班级", description = "更新指定班级信息")
    public ApiResponse<ClassDto> updateClass(
            @PathVariable Long classId,
            @Valid @RequestBody ClassDto classDto) {
        ClassDto result = classService.updateClass(classId, classDto);
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/{classId}")
    @Operation(summary = "删除班级", description = "删除指定班级")
    public ApiResponse<Void> deleteClass(@PathVariable Long classId) {
        classService.deleteClass(classId);
        return ApiResponse.ok();
    }

    @GetMapping("/{classId}")
    @Operation(summary = "获取班级详情", description = "根据班级ID获取班级详细信息")
    public ApiResponse<ClassDto> getClassById(@PathVariable Long classId) {
        ClassDto classDto = classService.getClassById(classId);
        return ApiResponse.ok(classDto);
    }

    @GetMapping
    @Operation(summary = "获取班级列表", description = "列出所有班级，可按课程筛选")
    public ApiResponse<List<ClassDto>> listClasses(@RequestParam(required = false) Long courseId) {
        List<ClassDto> classes;
        if (courseId != null) {
            classes = classService.getClassesByCourseId(courseId);
        } else {
            classes = classService.getAllClasses();
        }
        return ApiResponse.ok(classes);
    }

    @GetMapping("/{classId}/students")
    @Operation(summary = "获取班级学生列表", description = "获取指定班级的所有学生")
    public ApiResponse<List<UserResponse>> listClassStudents(@PathVariable Long classId) {
        List<UserResponse> students = classService.getClassStudents(classId);
        return ApiResponse.ok(students);
    }

    @PostMapping("/{classId}/students")
    @Operation(summary = "批量导入学生到班级", description = "批量将学生添加到指定班级")
    public ApiResponse<ImportResultDto> importStudentsToClass(
            @PathVariable Long classId,
            @Valid @RequestBody StudentImportDto studentImportDto) {
        ImportResultDto result = classService.importStudentsToClass(classId, studentImportDto);
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    @Operation(summary = "从班级移除学生", description = "从指定班级中移除一个学生")
    public ApiResponse<Void> removeStudentFromClass(
            @PathVariable Long classId,
            @PathVariable String studentId) {
        classService.removeStudentFromClass(classId, studentId);
        return ApiResponse.ok();
    }

    @GetMapping("/{classId}/tas")
    @Operation(summary = "获取班级助教列表", description = "获取指定班级的所有助教")
    public ApiResponse<List<UserResponse>> listClassTeachingAssistants(@PathVariable Long classId) {
        List<UserResponse> tas = classService.getClassTeachingAssistants(classId);
        return ApiResponse.ok(tas);
    }

    @PostMapping("/{classId}/tas")
    @Operation(summary = "添加助教到班级", description = "批量将助教添加到指定班级")
    public ApiResponse<ImportResultDto> addTeachingAssistantsToClass(
            @PathVariable Long classId,
            @Valid @RequestBody TAImportDto taImportDto) {
        ImportResultDto result = classService.addTeachingAssistantsToClass(classId, taImportDto);
        return ApiResponse.ok(result);
    }

    @PostMapping("/{classId}/enroll")
    @Operation(summary = "学生加入班级", description = "当前登录学生报名加入指定班级")
    public ApiResponse<Void> enrollInClass(@PathVariable Long classId) {
        String currentStudentId = SecurityUtils.getCurrentUserId();
        if (currentStudentId == null) {
            throw new RuntimeException("用户未登录");
        }
        if (!SecurityUtils.isStudent()) {
            throw new RuntimeException("只有学生可以加入班级");
        }
        classService.enrollStudentInClass(classId, currentStudentId);
        return ApiResponse.ok();
    }

    @GetMapping("/{classId}/resources")
    @Operation(summary = "获取班级资料列表", description = "获取指定班级可见的所有资料")
    public ApiResponse<List<MaterialDto>> getClassResources(@PathVariable Long classId) {
        List<MaterialDto> materials = classService.getClassMaterials(classId);
        return ApiResponse.ok(materials);
    }

    @GetMapping("/{classId}/students/{studentId}/assignment-summary")
    @Operation(summary = "获取学生作业完成摘要", description = "获取指定学生在特定班级中的作业完成情况摘要")
    public ApiResponse<StudentAssignmentSummaryDto> getStudentAssignmentSummary(
            @PathVariable Long classId,
            @PathVariable String studentId) {
        StudentAssignmentSummaryDto summary = classService.getStudentAssignmentSummary(classId, studentId);
        return ApiResponse.ok(summary);
    }

}
