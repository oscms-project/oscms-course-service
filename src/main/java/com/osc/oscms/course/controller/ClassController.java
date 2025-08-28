package com.osc.oscms.course.controller;

import com.osc.oscms.course.dto.*;
import com.osc.oscms.course.service.ClassService;
import com.osc.oscms.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 班级控制器
 */
@RestController
@RequestMapping("/api/classes")
@Tag(name = "班级管理", description = "班级相关API")
@Validated
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping
    @Operation(summary = "创建班级")
    public CommonResponse<ClassResponse> createClass(@Valid @RequestBody ClassCreateRequest request) {
        ClassResponse response = classService.createClass(request);
        return CommonResponse.success(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新班级")
    public CommonResponse<ClassResponse> updateClass(@PathVariable Long id,
            @Valid @RequestBody ClassUpdateRequest request) {
        ClassResponse response = classService.updateClass(id, request);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除班级")
    public CommonResponse<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return CommonResponse.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取班级")
    public CommonResponse<ClassResponse> getClassById(@PathVariable Long id) {
        ClassResponse response = classService.getClassById(id);
        return CommonResponse.success(response);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "根据课程ID获取班级列表")
    public CommonResponse<List<ClassResponse>> getClassesByCourseId(@PathVariable Long courseId) {
        List<ClassResponse> response = classService.getClassesByCourseId(courseId);
        return CommonResponse.success(response);
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "根据教师ID获取班级列表")
    public CommonResponse<List<ClassResponse>> getClassesByInstructorId(@PathVariable Long instructorId) {
        List<ClassResponse> response = classService.getClassesByInstructorId(instructorId);
        return CommonResponse.success(response);
    }

    @GetMapping("/semester/{semester}/year/{year}")
    @Operation(summary = "根据学期和年份获取班级列表")
    public CommonResponse<List<ClassResponse>> getClassesBySemesterAndYear(@PathVariable String semester,
            @PathVariable Integer year) {
        List<ClassResponse> response = classService.getClassesBySemesterAndYear(semester, year);
        return CommonResponse.success(response);
    }

    @PostMapping("/{classId}/students/{studentId}")
    @Operation(summary = "学生加入班级")
    public CommonResponse<Void> addStudentToClass(@PathVariable Long classId,
            @PathVariable Long studentId,
            @RequestParam String studentName,
            @RequestParam String studentEmail) {
        classService.addStudentToClass(classId, studentId, studentName, studentEmail);
        return CommonResponse.success();
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    @Operation(summary = "学生退出班级")
    public CommonResponse<Void> removeStudentFromClass(@PathVariable Long classId,
            @PathVariable Long studentId) {
        classService.removeStudentFromClass(classId, studentId);
        return CommonResponse.success();
    }

    @PostMapping("/{classId}/tas/{taId}")
    @Operation(summary = "分配助教到班级")
    public CommonResponse<Void> assignTAToClass(@PathVariable Long classId,
            @PathVariable Long taId,
            @RequestParam String taName,
            @RequestParam String taEmail) {
        classService.assignTAToClass(classId, taId, taName, taEmail);
        return CommonResponse.success();
    }

    @DeleteMapping("/{classId}/tas/{taId}")
    @Operation(summary = "移除班级助教")
    public CommonResponse<Void> removeTAFromClass(@PathVariable Long classId,
            @PathVariable Long taId) {
        classService.removeTAFromClass(classId, taId);
        return CommonResponse.success();
    }

    @GetMapping("/{classId}/students")
    @Operation(summary = "获取班级学生列表")
    public CommonResponse<List<Object>> getClassStudents(@PathVariable Long classId) {
        List<Object> response = classService.getClassStudents(classId);
        return CommonResponse.success(response);
    }

    @GetMapping("/{classId}/tas")
    @Operation(summary = "获取班级助教列表")
    public CommonResponse<List<Object>> getClassTAs(@PathVariable Long classId) {
        List<Object> response = classService.getClassTAs(classId);
        return CommonResponse.success(response);
    }
}
