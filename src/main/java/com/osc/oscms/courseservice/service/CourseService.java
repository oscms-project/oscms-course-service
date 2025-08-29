package com.osc.oscms.courseservice.service;

import com.osc.oscms.common.dto.course.CourseCreateDto;
import com.osc.oscms.common.dto.course.CourseDto;

import java.util.List;

/**
 * 课程服务接口
 */
public interface CourseService {

    /**
     * 创建课程
     */
    CourseDto createCourse(String teacherId, CourseCreateDto courseCreateDto);

    /**
     * 更新课程
     */
    CourseDto updateCourse(Long courseId, CourseDto courseDto);

    /**
     * 删除课程
     */
    void deleteCourse(Long courseId);

    /**
     * 根据ID获取课程详情
     */
    CourseDto getCourseById(Long courseId);

    /**
     * 获取所有课程
     */
    List<CourseDto> getAllCourses();

    /**
     * 根据教师ID获取课程列表
     */
    List<CourseDto> getCoursesByTeacherId(String teacherId);

    /**
     * 获取活跃课程
     */
    List<CourseDto> getActiveCourses();

    /**
     * 获取已结课程
     */
    List<CourseDto> getCompletedCourses();

    /**
     * 搜索课程
     */
    List<CourseDto> searchCourses(String keyword);

    /**
     * 标记课程为已完成
     */
    void completeCourse(Long courseId, String teacherId);

    /**
     * 重新开启课程
     */
    void reopenCourse(Long courseId, String teacherId);
}
