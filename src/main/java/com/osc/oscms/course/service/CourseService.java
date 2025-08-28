package com.osc.oscms.course.service;

import com.osc.oscms.course.dto.*;

import java.util.List;

/**
 * 课程服务接口
 */
public interface CourseService {

    /**
     * 创建课程
     */
    CourseResponse createCourse(CourseCreateRequest request);

    /**
     * 更新课程
     */
    CourseResponse updateCourse(Long id, CourseUpdateRequest request);

    /**
     * 删除课程
     */
    void deleteCourse(Long id);

    /**
     * 根据ID获取课程
     */
    CourseResponse getCourseById(Long id);

    /**
     * 获取所有课程
     */
    List<CourseResponse> getAllCourses();

    /**
     * 根据教师ID获取课程列表
     */
    List<CourseResponse> getCoursesByInstructorId(Long instructorId);

    /**
     * 根据状态获取课程列表
     */
    List<CourseResponse> getCoursesByStatus(String status);

    /**
     * 搜索课程
     */
    List<CourseResponse> searchCourses(String keyword);

    /**
     * 激活课程
     */
    CourseResponse activateCourse(Long id);

    /**
     * 停用课程
     */
    CourseResponse deactivateCourse(Long id);
}
