package com.osc.oscms.courseservice.controller;

import com.osc.oscms.common.response.ApiResponse;
import com.osc.oscms.common.dto.course.CourseCreateDto;
import com.osc.oscms.common.dto.course.CourseDto;
import com.osc.oscms.common.dto.clazz.ClassDto;
import com.osc.oscms.common.exception.UnauthorizedException;
import com.osc.oscms.courseservice.service.CourseService;
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
 * 课程管理控制器
 */
@RestController
@RequestMapping("/courses")
@Tag(name = "Course Management", description = "课程管理接口")
@RequiredArgsConstructor
@Validated
public class CourseController {

    private final CourseService courseService;
    private final ClassService classService;

    @PostMapping
    @Operation(summary = "创建课程", description = "教师创建新课程")
    public ApiResponse<CourseDto> createCourse(
            @RequestParam String teacherId,
            @Valid @RequestBody CourseCreateDto courseCreateDto) {
        // 验证当前用户是否是教师且与请求中的teacherId一致
        String currentUserId = SecurityUtils.getCurrentUserId();
        if (!SecurityUtils.isTeacher() || !teacherId.equals(currentUserId)) {
            throw new RuntimeException("只有教师本人可以创建课程");
        }

        CourseDto result = courseService.createCourse(teacherId, courseCreateDto);
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
    @Operation(summary = "获取教师的所有课程", description = "列出指定教师的所有课程")
    public ApiResponse<List<CourseDto>> listCoursesByTeacher(@RequestParam String teacherId) {
        // 验证当前用户是否是教师且与请求中的teacherId一致
        String currentUserId = SecurityUtils.getCurrentUserId();
        if (!SecurityUtils.isTeacher() || !teacherId.equals(currentUserId)) {
            throw new RuntimeException("只有教师本人可以查看自己的课程");
        }

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
    public ApiResponse<Void> completeCourse(
            @PathVariable Long courseId,
            @RequestParam String teacherId) {

        // 验证权限：只有课程的教师可以标记完成
        String currentUserId = SecurityUtils.getCurrentUserId();
        if (!teacherId.equals(currentUserId)) {
            throw new UnauthorizedException("只有课程教师可以标记课程完成");
        }

        courseService.completeCourse(courseId, teacherId);
        return ApiResponse.ok();
    }

    @PostMapping("/{courseId}/reopen")
    @Operation(summary = "重新开启课程", description = "将已完成的课程重新开启")
    public ApiResponse<Void> reopenCourse(
            @PathVariable Long courseId,
            @RequestParam String teacherId) {

        // 验证权限：只有课程的教师可以重新开启
        String currentUserId = SecurityUtils.getCurrentUserId();
        if (!teacherId.equals(currentUserId)) {
            throw new UnauthorizedException("只有课程教师可以重新开启课程");
        }

        courseService.reopenCourse(courseId, teacherId);
        return ApiResponse.ok();
    }

    @GetMapping("/{courseId}/classes")
    @Operation(summary = "获取课程下的所有班级", description = "获取指定课程下的所有班级列表")
    public ApiResponse<List<ClassDto>> getCourseClasses(@PathVariable Long courseId) {
        List<ClassDto> classes = classService.getClassesByCourseId(courseId);
        return ApiResponse.ok(classes);
    }
}
