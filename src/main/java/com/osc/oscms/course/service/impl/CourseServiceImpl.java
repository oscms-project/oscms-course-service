package com.osc.oscms.course.service.impl;

import com.osc.oscms.course.dto.*;
import com.osc.oscms.course.domain.Course;
import com.osc.oscms.course.mapper.CourseMapper;
import com.osc.oscms.course.service.CourseService;
import com.osc.oscms.common.exception.BusinessException;
import com.osc.oscms.common.response.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程服务实现类
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public CourseResponse createCourse(CourseCreateRequest request) {
        Course course = new Course();
        BeanUtils.copyProperties(request, course);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        int result = courseMapper.insert(course);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "创建课程失败");
        }

        return convertToResponse(course);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "课程不存在");
        }

        // 只更新非空字段
        if (request.getName() != null) {
            course.setName(request.getName());
        }
        if (request.getDescription() != null) {
            course.setDescription(request.getDescription());
        }
        if (request.getInstructorId() != null) {
            course.setInstructorId(request.getInstructorId());
        }
        if (request.getInstructorName() != null) {
            course.setInstructorName(request.getInstructorName());
        }
        if (request.getCredits() != null) {
            course.setCredits(request.getCredits());
        }
        if (request.getStatus() != null) {
            course.setStatus(request.getStatus());
        }
        course.setUpdatedAt(LocalDateTime.now());

        int result = courseMapper.updateById(course);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "更新课程失败");
        }

        return convertToResponse(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "课程不存在");
        }

        course.setStatus("DELETED");
        course.setUpdatedAt(LocalDateTime.now());

        int result = courseMapper.updateById(course);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "删除课程失败");
        }
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null || "DELETED".equals(course.getStatus())) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "课程不存在");
        }

        return convertToResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseMapper.selectList(null);
        return courses.stream()
                .filter(course -> !"DELETED".equals(course.getStatus()))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getCoursesByInstructorId(Long instructorId) {
        List<Course> courses = courseMapper.findByInstructorId(instructorId);
        return courses.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> getCoursesByStatus(String status) {
        List<Course> courses = courseMapper.findByStatus(status);
        return courses.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> searchCourses(String keyword) {
        List<Course> courses = courseMapper.searchCourses(keyword);
        return courses.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse activateCourse(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "课程不存在");
        }

        course.setStatus("ACTIVE");
        course.setUpdatedAt(LocalDateTime.now());

        int result = courseMapper.updateById(course);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "激活课程失败");
        }

        return convertToResponse(course);
    }

    @Override
    public CourseResponse deactivateCourse(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "课程不存在");
        }

        course.setStatus("INACTIVE");
        course.setUpdatedAt(LocalDateTime.now());

        int result = courseMapper.updateById(course);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "停用课程失败");
        }

        return convertToResponse(course);
    }

    private CourseResponse convertToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        BeanUtils.copyProperties(course, response);
        return response;
    }
}



