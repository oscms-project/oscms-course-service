package com.osc.oscms.courseservice.controller;

import com.osc.oscms.common.response.ApiResponse;
import com.osc.oscms.courseservice.dto.CourseCreateDto;
import com.osc.oscms.courseservice.dto.CourseDto;
import com.osc.oscms.courseservice.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 课程管理控制器
 */
@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management", description = "课程管理接口")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @Operation(summary = "创建课程", description = "创建新的课程")
    public ApiResponse<CourseDto> createCourse(@Valid @RequestBody CourseCreateDto courseCreateDto) {
        CourseDto result = courseService.createCourse(courseCreateDto);
        return ApiResponse.ok(result);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "更新课程", description = "更新指定课程信息")
    public ApiResponse<CourseDto> updateCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody CourseDto courseDto) {
        CourseDto result = courseService.updateCourse(courseId, courseDto);
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "删除课程", description = "删除指定课程")
    public ApiResponse<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ApiResponse.ok();
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "获取课程详情", description = "根据课程ID获取课程详细信息")
    public ApiResponse<CourseDto> getCourseById(@PathVariable Long courseId) {
        CourseDto course = courseService.getCourseById(courseId);
        return ApiResponse.ok(course);
    }

    @GetMapping
    @Operation(summary = "获取所有课程", description = "获取所有课程列表")
    public ApiResponse<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        return ApiResponse.ok(courses);
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "获取教师课程", description = "根据教师ID获取该教师的所有课程")
    public ApiResponse<List<CourseDto>> getCoursesByTeacher(@PathVariable String teacherId) {
        List<CourseDto> courses = courseService.getCoursesByTeacherId(teacherId);
        return ApiResponse.ok(courses);
    }

    @GetMapping("/active")
    @Operation(summary = "获取活跃课程", description = "获取所有未结课的课程")
    public ApiResponse<List<CourseDto>> getActiveCourses() {
        List<CourseDto> courses = courseService.getActiveCourses();
        return ApiResponse.ok(courses);
    }

    @GetMapping("/completed")
    @Operation(summary = "获取已结课程", description = "获取所有已结课的课程")
    public ApiResponse<List<CourseDto>> getCompletedCourses() {
        List<CourseDto> courses = courseService.getCompletedCourses();
        return ApiResponse.ok(courses);
    }

    @GetMapping("/search")
    @Operation(summary = "搜索课程", description = "根据关键词搜索课程")
    public ApiResponse<List<CourseDto>> searchCourses(@RequestParam String keyword) {
        List<CourseDto> courses = courseService.searchCourses(keyword);
        return ApiResponse.ok(courses);
    }

    @PostMapping("/{courseId}/complete")
    @Operation(summary = "标记课程完成", description = "将课程标记为已完成")
    public ApiResponse<Void> markCourseAsCompleted(@PathVariable Long courseId) {
        courseService.markCourseAsCompleted(courseId);
        return ApiResponse.ok();
    }

    @PostMapping("/{courseId}/reopen")
    @Operation(summary = "重新开启课程", description = "重新开启已完成的课程")
    public ApiResponse<Void> reopenCourse(@PathVariable Long courseId) {
        courseService.reopenCourse(courseId);
        return ApiResponse.ok();
    }
}
