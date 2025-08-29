package com.osc.oscms.courseservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.osc.oscms.courseservice.domain.Course;
import com.osc.oscms.courseservice.domain.Chapter;
import com.osc.oscms.common.dto.course.CourseCreateDto;
import com.osc.oscms.common.dto.course.CourseDto;
import com.osc.oscms.courseservice.repository.CourseRepository;
import com.osc.oscms.courseservice.repository.ChapterRepository;
import com.osc.oscms.courseservice.service.CourseService;
import com.osc.oscms.common.exception.BusinessException;
import com.osc.oscms.common.exception.CourseException.CourseCodeAlreadyExistsException;
import com.osc.oscms.common.exception.CourseException.CourseNotFoundException;
import com.osc.oscms.common.dto.course.ChapterCreateDto;
import com.osc.oscms.courseservice.security.SecurityUtils;
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
    public CourseDto createCourse(String teacherId, CourseCreateDto courseCreateDto) {
        log.info("Creating course: {} for teacher: {}", courseCreateDto.getName(), teacherId);

        // 检查课程代码是否已存在
        if (courseRepository.findByCode(courseCreateDto.getCode()) != null) {
            throw new CourseCodeAlreadyExistsException("课程代码已存在: " + courseCreateDto.getCode());
        }

        Course course = new Course();
        BeanUtils.copyProperties(courseCreateDto, course);
        course.setTeacherId(teacherId);
        course.setCompleted(courseCreateDto.getCompleted() != null ? courseCreateDto.getCompleted() : false);

        courseRepository.insert(course);

        // 创建章节
        if (courseCreateDto.getChapters() != null && !courseCreateDto.getChapters().isEmpty()) {
            createChapters(course.getId(), courseCreateDto.getChapters());
        }

        return convertToDto(course);
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long courseId, CourseDto courseDto) {
        log.info("Updating course: {}", courseId);

        Course course = getCourseByIdOrThrow(courseId);

        // TODO: 临时注释认证检查，用于测试
        // 权限检查：只有课程的教师可以更新课程
        // String currentUserId = SecurityUtils.getCurrentUserId();
        // if (!SecurityUtils.isTeacher() ||
        // !course.getTeacherId().equals(currentUserId)) {
        // throw new RuntimeException("只有课程教师可以更新课程信息");
        // }

        // 更新字段
        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }
        if (courseDto.getCode() != null && !courseDto.getCode().equals(course.getCode())) {
            // 检查新代码是否已存在
            if (courseRepository.findByCode(courseDto.getCode()) != null) {
                throw new CourseCodeAlreadyExistsException("课程代码已存在: " + courseDto.getCode());
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

        // 处理章节更新
        if (courseDto.getChapters() != null) {
            updateCourseChapters(courseId, courseDto.getChapters());
        }

        courseRepository.updateById(course);

        return convertToDto(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        log.info("Deleting course: {}", courseId);

        Course course = getCourseByIdOrThrow(courseId);

        // TODO: 临时注释认证检查，用于测试
        // 权限检查：只有课程的教师可以删除课程
        // String currentUserId = SecurityUtils.getCurrentUserId();
        // if (!SecurityUtils.isTeacher() ||
        // !course.getTeacherId().equals(currentUserId)) {
        // throw new RuntimeException("只有课程教师可以删除课程");
        // }

        // 删除课程的所有章节
        chapterRepository.deleteByCourseId(courseId);

        // 删除课程
        courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        Course course = getCourseByIdOrThrow(courseId);
        return convertToDto(course);
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
    public void completeCourse(Long courseId, String teacherId) {
        log.info("Completing course: {} by teacher: {}", courseId, teacherId);

        Course course = getCourseByIdOrThrow(courseId);

        // TODO: 临时注释认证检查，用于测试
        // 验证权限：只有课程的教师可以标记完成
        // if (!teacherId.equals(course.getTeacherId())) {
        // throw new RuntimeException("只有课程教师可以标记课程完成");
        // }

        course.setCompleted(true);

        courseRepository.updateById(course);
        log.info("Course {} marked as completed", courseId);
    }

    @Override
    @Transactional
    public void reopenCourse(Long courseId, String teacherId) {
        log.info("Reopening course: {} by teacher: {}", courseId, teacherId);

        Course course = getCourseByIdOrThrow(courseId);

        // TODO: 临时注释认证检查，用于测试
        // 验证权限：只有课程的教师可以重新开启
        // if (!teacherId.equals(course.getTeacherId())) {
        // throw new RuntimeException("只有课程教师可以重新开启课程");
        // }

        course.setCompleted(false);

        courseRepository.updateById(course);
        log.info("Course {} reopened", courseId);
    }

    private Course getCourseByIdOrThrow(Long courseId) {
        Course course = courseRepository.selectById(courseId);
        if (course == null) {
            throw new CourseNotFoundException("课程未找到: " + courseId);
        }
        return course;
    }

    private CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        BeanUtils.copyProperties(course, dto);

        // 加载章节信息
        List<Chapter> chapters = chapterRepository.findByCourseId(course.getId());
        if (chapters != null && !chapters.isEmpty()) {
            List<ChapterCreateDto> chapterDtos = chapters.stream()
                    .map(this::convertChapterToDto)
                    .collect(Collectors.toList());
            dto.setChapters(chapterDtos);
        }

        return dto;
    }

    private void createChapters(Long courseId, List<ChapterCreateDto> chapterDtos) {
        for (ChapterCreateDto chapterDto : chapterDtos) {
            Chapter chapter = new Chapter();
            chapter.setCourseId(courseId);
            chapter.setTitle(chapterDto.getTitle());
            chapter.setOrder(chapterDto.getOrder() != null ? chapterDto.getOrder() : 0);
            chapterRepository.insert(chapter);
        }
    }

    private ChapterCreateDto convertChapterToDto(Chapter chapter) {
        ChapterCreateDto dto = new ChapterCreateDto();
        dto.setTitle(chapter.getTitle());
        dto.setOrder(chapter.getOrder());
        return dto;
    }

    private void updateCourseChapters(Long courseId, List<ChapterCreateDto> chapterDtos) {
        // 删除现有章节
        chapterRepository.deleteByCourseId(courseId);

        // 创建新章节
        if (!chapterDtos.isEmpty()) {
            createChapters(courseId, chapterDtos);
        }
    }
}
