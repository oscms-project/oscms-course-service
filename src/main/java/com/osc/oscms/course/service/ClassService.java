package com.osc.oscms.course.service;

import com.osc.oscms.course.dto.*;

import java.util.List;

/**
 * 班级服务接口
 */
public interface ClassService {

    /**
     * 创建班级
     */
    ClassResponse createClass(ClassCreateRequest request);

    /**
     * 更新班级
     */
    ClassResponse updateClass(Long id, ClassUpdateRequest request);

    /**
     * 删除班级
     */
    void deleteClass(Long id);

    /**
     * 根据ID获取班级
     */
    ClassResponse getClassById(Long id);

    /**
     * 根据课程ID获取班级列表
     */
    List<ClassResponse> getClassesByCourseId(Long courseId);

    /**
     * 根据教师ID获取班级列表
     */
    List<ClassResponse> getClassesByInstructorId(Long instructorId);

    /**
     * 根据学期和年份获取班级列表
     */
    List<ClassResponse> getClassesBySemesterAndYear(String semester, Integer year);

    /**
     * 学生加入班级
     */
    void addStudentToClass(Long classId, Long studentId, String studentName, String studentEmail);

    /**
     * 学生退出班级
     */
    void removeStudentFromClass(Long classId, Long studentId);

    /**
     * 分配助教到班级
     */
    void assignTAToClass(Long classId, Long taId, String taName, String taEmail);

    /**
     * 移除班级助教
     */
    void removeTAFromClass(Long classId, Long taId);

    /**
     * 获取班级学生列表
     */
    List<Object> getClassStudents(Long classId);

    /**
     * 获取班级助教列表
     */
    List<Object> getClassTAs(Long classId);
}



