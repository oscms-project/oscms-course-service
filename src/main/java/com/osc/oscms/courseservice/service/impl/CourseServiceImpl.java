package com.osc.oscms.courseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osc.oscms.courseservice.domain.Course;
import com.osc.oscms.courseservice.domain.Chapter;
import com.osc.oscms.courseservice.dto.CourseCreateDto;
import com.osc.oscms.courseservice.dto.CourseDto;
import com.osc.oscms.courseservice.repository.CourseRepository;
import com.osc.oscms.courseservice.repository.ChapterRepository;
import com.osc.oscms.courseservice.service.CourseService;
import com.osc.oscms.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    private final ChapterRepository chapterRepository;
    
    @Override
    @Transactional
    public CourseDto createCourse(CourseCreateDto courseCreateDto) {
        log.info("Creating course: {}", courseCreateDto.getName());
        
        // 检查课程代码是否已存在
        if (courseRepository.findByCode(courseCreateDto.getCode()) != null) {
            throw new BusinessException("课程代码已存在");
        }
        
        Course course = new Course();
        BeanUtils.copyProperties(courseCreateDto, course);
        course.setCompleted(false);
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        
        courseRepository.insert(course);
        
        return convertToDto(course);
    }
    
    @Override
    @Transactional
    public CourseDto updateCourse(Long courseId, CourseDto courseDto) {
        log.info("Updating course: {}", courseId);
        
        Course course = getCourseByIdOrThrow(courseId);
        
        // 更新字段
        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }
        if (courseDto.getCode() != null && !courseDto.getCode().equals(course.getCode())) {
            // 检查新代码是否已存在
            if (courseRepository.findByCode(courseDto.getCode()) != null) {
                throw new BusinessException("课程代码已存在");
            }
            course.setCode(courseDto.getCode());
        }
        if (courseDto.getOutline() != null) {
            course.setOutline(courseDto.getOutline());
        }
        if (courseDto.getObjectives() != null) {
            course.setObjectives(courseDto.getObjectives());
        }
        if (courseDto.getAssessment() != null) {
            course.setAssessment(courseDto.getAssessment());
        }
        
        course.setUpdatedAt(LocalDateTime.now());
        
        courseRepository.updateById(course);
        
        return convertToDto(course);
    }
    
    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        log.info("Deleting course: {}", courseId);
        
        Course course = getCourseByIdOrThrow(courseId);
        
        // 删除课程的所有章节
        chapterRepository.deleteByCourseId(courseId);
        
        // 删除课程
        courseRepository.deleteById(courseId);
    }
    
    @Override
    public CourseDto getCourseById(Long courseId) {
        Course course = getCourseByIdOrThrow(courseId);
        CourseDto dto = convertToDto(course);
        
        // 加载章节信息
        List<Chapter> chapters = chapterRepository.findByCourseId(courseId);
        course.setChapters(chapters);
        
        return dto;
    }
    
    @Override
    public List<CourseDto> getAllCourses() {
        log.info("Getting all courses");
        
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at");
        
        List<Course> courses = courseRepository.selectList(queryWrapper);
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CourseDto> getCoursesByTeacherId(String teacherId) {
        log.info("Getting courses for teacher: {}", teacherId);
        
        List<Course> courses = courseRepository.findByTeacherId(teacherId);
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CourseDto> getActiveCourses() {
        log.info("Getting active courses");
        
        List<Course> courses = courseRepository.findActiveCourses();
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CourseDto> getCompletedCourses() {
        log.info("Getting completed courses");
        
        List<Course> courses = courseRepository.findCompletedCourses();
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CourseDto> searchCourses(String keyword) {
        log.info("Searching courses with keyword: {}", keyword);
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCourses();
        }
        
        List<Course> courses = courseRepository.searchByName(keyword.trim());
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void markCourseAsCompleted(Long courseId) {
        log.info("Marking course as completed: {}", courseId);
        
        Course course = getCourseByIdOrThrow(courseId);
        course.setCompleted(true);
        course.setUpdatedAt(LocalDateTime.now());
        
        courseRepository.updateById(course);
    }
    
    @Override
    @Transactional
    public void reopenCourse(Long courseId) {
        log.info("Reopening course: {}", courseId);
        
        Course course = getCourseByIdOrThrow(courseId);
        course.setCompleted(false);
        course.setUpdatedAt(LocalDateTime.now());
        
        courseRepository.updateById(course);
    }
    
    private Course getCourseByIdOrThrow(Long courseId) {
        Course course = courseRepository.selectById(courseId);
        if (course == null) {
            throw new BusinessException("课程不存在");
        }
        return course;
    }
    
    private CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        BeanUtils.copyProperties(course, dto);
        return dto;
    }
}
