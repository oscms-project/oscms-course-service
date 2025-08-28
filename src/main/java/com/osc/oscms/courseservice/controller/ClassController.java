package com.osc.oscms.courseservice.controller;

import com.osc.oscms.common.response.ApiResponse;
import com.osc.oscms.courseservice.dto.ClassCreateDto;
import com.osc.oscms.courseservice.dto.ClassDto;
import com.osc.oscms.courseservice.service.ClassService;
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

    @GetMapping("/course/{courseId}")
    @Operation(summary = "获取课程班级", description = "根据课程ID获取该课程的所有班级")
    public ApiResponse<List<ClassDto>> getClassesByCourse(@PathVariable Long courseId) {
        List<ClassDto> classes = classService.getClassesByCourseId(courseId);
        return ApiResponse.ok(classes);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "获取学生班级", description = "根据学生ID获取该学生所在的班级")
    public ApiResponse<List<ClassDto>> getClassesByStudent(@PathVariable String studentId) {
        List<ClassDto> classes = classService.getClassesByStudentId(studentId);
        return ApiResponse.ok(classes);
    }

    @GetMapping("/ta/{taId}")
    @Operation(summary = "获取助教班级", description = "根据助教ID获取该助教管理的班级")
    public ApiResponse<List<ClassDto>> getClassesByTA(@PathVariable String taId) {
        List<ClassDto> classes = classService.getClassesByTaId(taId);
        return ApiResponse.ok(classes);
    }

    @PostMapping("/{classId}/students/{studentId}")
    @Operation(summary = "添加学生到班级", description = "将学生添加到指定班级")
    public ApiResponse<Void> addStudentToClass(
            @PathVariable Long classId,
            @PathVariable String studentId) {
        classService.addStudentToClass(classId, studentId);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    @Operation(summary = "从班级移除学生", description = "将学生从指定班级移除")
    public ApiResponse<Void> removeStudentFromClass(
            @PathVariable Long classId,
            @PathVariable String studentId) {
        classService.removeStudentFromClass(classId, studentId);
        return ApiResponse.ok();
    }

    @PostMapping("/{classId}/tas/{taId}")
    @Operation(summary = "添加助教到班级", description = "将助教添加到指定班级")
    public ApiResponse<Void> addTAToClass(
            @PathVariable Long classId,
            @PathVariable String taId) {
        classService.addTAToClass(classId, taId);
        return ApiResponse.ok();
    }

    @DeleteMapping("/{classId}/tas/{taId}")
    @Operation(summary = "从班级移除助教", description = "将助教从指定班级移除")
    public ApiResponse<Void> removeTAFromClass(
            @PathVariable Long classId,
            @PathVariable String taId) {
        classService.removeTAFromClass(classId, taId);
        return ApiResponse.ok();
    }

    @GetMapping("/{classId}/students")
    @Operation(summary = "获取班级学生", description = "获取指定班级的所有学生ID")
    public ApiResponse<List<String>> getClassStudents(@PathVariable Long classId) {
        List<String> studentIds = classService.getClassStudentIds(classId);
        return ApiResponse.ok(studentIds);
    }

    @GetMapping("/{classId}/tas")
    @Operation(summary = "获取班级助教", description = "获取指定班级的所有助教ID")
    public ApiResponse<List<String>> getClassTAs(@PathVariable Long classId) {
        List<String> taIds = classService.getClassTAIds(classId);
        return ApiResponse.ok(taIds);
    }

    @PostMapping("/{classId}/students/batch")
    @Operation(summary = "批量添加学生", description = "批量将学生添加到班级")
    public ApiResponse<Void> addStudentsToClass(
            @PathVariable Long classId,
            @RequestBody List<String> studentIds) {
        classService.addStudentsToClass(classId, studentIds);
        return ApiResponse.ok();
    }

    @PostMapping("/{classId}/tas/batch")
    @Operation(summary = "批量添加助教", description = "批量将助教添加到班级")
    public ApiResponse<Void> addTAsToClass(
            @PathVariable Long classId,
            @RequestBody List<String> taIds) {
        classService.addTAsToClass(classId, taIds);
        return ApiResponse.ok();
    }
}
