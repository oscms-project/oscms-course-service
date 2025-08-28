package com.osc.oscms.course.controller;

import com.osc.oscms.course.dto.*;
import com.osc.oscms.course.service.CourseService;
import com.osc.oscms.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 课程控制器
 */
@RestController
@RequestMapping("/api/courses")
@Tag(name = "课程管理", description = "课程相关API")
@Validated
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "创建课程")
    public CommonResponse<CourseResponse> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return CommonResponse.success(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新课程")
    public CommonResponse<CourseResponse> updateCourse(@PathVariable Long id,
            @Valid @RequestBody CourseUpdateRequest request) {
        CourseResponse response = courseService.updateCourse(id, request);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除课程")
    public CommonResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return CommonResponse.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取课程")
    public CommonResponse<CourseResponse> getCourseById(@PathVariable Long id) {
        CourseResponse response = courseService.getCourseById(id);
        return CommonResponse.success(response);
    }

    @GetMapping
    @Operation(summary = "获取所有课程")
    public CommonResponse<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> response = courseService.getAllCourses();
        return CommonResponse.success(response);
    }

    @GetMapping("/instructor/{instructorId}")
    @Operation(summary = "根据教师ID获取课程列表")
    public CommonResponse<List<CourseResponse>> getCoursesByInstructorId(@PathVariable Long instructorId) {
        List<CourseResponse> response = courseService.getCoursesByInstructorId(instructorId);
        return CommonResponse.success(response);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态获取课程列表")
    public CommonResponse<List<CourseResponse>> getCoursesByStatus(@PathVariable String status) {
        List<CourseResponse> response = courseService.getCoursesByStatus(status);
        return CommonResponse.success(response);
    }

    @GetMapping("/search")
    @Operation(summary = "搜索课程")
    public CommonResponse<List<CourseResponse>> searchCourses(@RequestParam String keyword) {
        List<CourseResponse> response = courseService.searchCourses(keyword);
        return CommonResponse.success(response);
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "激活课程")
    public CommonResponse<CourseResponse> activateCourse(@PathVariable Long id) {
        CourseResponse response = courseService.activateCourse(id);
        return CommonResponse.success(response);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "停用课程")
    public CommonResponse<CourseResponse> deactivateCourse(@PathVariable Long id) {
        CourseResponse response = courseService.deactivateCourse(id);
        return CommonResponse.success(response);
    }
}
