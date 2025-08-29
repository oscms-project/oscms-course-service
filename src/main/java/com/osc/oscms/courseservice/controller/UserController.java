package com.osc.oscms.courseservice.controller;

import com.osc.oscms.common.response.ApiResponse;
import com.osc.oscms.common.dto.course.CourseDto;
import com.osc.oscms.common.dto.clazz.ClassDto;
import com.osc.oscms.common.dto.clazz.StudentClassInfoDto;
import com.osc.oscms.courseservice.service.ClassService;
import com.osc.oscms.courseservice.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关的课程和班级信息控制器
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Course Management", description = "用户课程管理接口")
@RequiredArgsConstructor
public class UserController {

    private final CourseService courseService;
    private final ClassService classService;

    @GetMapping("/{userId}/courses")
    @Operation(summary = "获取学生参与的所有课程", description = "获取指定学生参与的所有课程列表")
    public ApiResponse<List<CourseDto>> getStudentCourses(@PathVariable String userId) {
        List<CourseDto> courses = courseService.getStudentCourses(userId);
        return ApiResponse.ok(courses);
    }

    @GetMapping("/{userId}/courses/{courseId}/class")
    @Operation(summary = "获取学生在某课程中的班级信息", description = "获取指定学生在某个课程中的班级信息")
    public ApiResponse<ClassDto> getStudentClassInCourse(
            @PathVariable String userId,
            @PathVariable Long courseId) {
        ClassDto classDto = classService.getStudentClassInCourse(userId, courseId);
        return ApiResponse.ok(classDto);
    }

    @GetMapping("/{studentId}/classes/info")
    @Operation(summary = "获取学生加入的所有班级信息", description = "获取指定学生加入的所有班级详细信息")
    public ApiResponse<List<StudentClassInfoDto>> getStudentEnrolledClassInfo(@PathVariable String studentId) {
        List<StudentClassInfoDto> classInfoList = classService.getStudentEnrolledClassInfo(studentId);
        return ApiResponse.ok(classInfoList);
    }
}
