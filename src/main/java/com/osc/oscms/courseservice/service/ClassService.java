package com.osc.oscms.courseservice.service;

import com.osc.oscms.common.dto.clazz.ClassCreateDto;
import com.osc.oscms.common.dto.clazz.ClassDto;
import com.osc.oscms.common.dto.clazz.StudentImportDto;
import com.osc.oscms.common.dto.clazz.TAImportDto;
import com.osc.oscms.common.dto.clazz.StudentClassInfoDto;
import com.osc.oscms.common.dto.clazz.StudentAssignmentSummaryDto;

import com.osc.oscms.common.dto.common.ImportResultDto;
import com.osc.oscms.common.dto.user.UserResponse;
import com.osc.oscms.common.dto.material.MaterialDto;

import java.util.List;

/**
 * 班级服务接口
 */
public interface ClassService {

    /**
     * 创建班级
     */
    ClassDto createClass(ClassCreateDto classCreateDto);

    /**
     * 更新班级
     */
    ClassDto updateClass(Long classId, ClassDto classDto);

    /**
     * 删除班级
     */
    void deleteClass(Long classId);

    /**
     * 根据ID获取班级详情
     */
    ClassDto getClassById(Long classId);

    /**
     * 根据课程ID获取班级列表
     */
    List<ClassDto> getClassesByCourseId(Long courseId);

    /**
     * 根据学生ID获取班级列表
     */
    List<ClassDto> getClassesByStudentId(String studentId);

    /**
     * 根据助教ID获取班级列表
     */
    List<ClassDto> getClassesByTaId(String taId);

    /**
     * 添加学生到班级
     */
    void addStudentToClass(Long classId, String studentId);

    /**
     * 从班级移除学生
     */
    void removeStudentFromClass(Long classId, String studentId);

    /**
     * 添加助教到班级
     */
    void addTAToClass(Long classId, String taId);

    /**
     * 从班级移除助教
     */
    void removeTAFromClass(Long classId, String taId);

    /**
     * 获取班级学生ID列表
     */
    List<String> getClassStudentIds(Long classId);

    /**
     * 获取班级助教ID列表
     */
    List<String> getClassTAIds(Long classId);

    /**
     * 批量添加学生到班级
     */
    void addStudentsToClass(Long classId, List<String> studentIds);

    /**
     * 批量添加助教到班级
     */
    void addTAsToClass(Long classId, List<String> taIds);

    /**
     * 获取所有班级
     */
    List<ClassDto> getAllClasses();

    /**
     * 获取班级学生列表（包含详细信息）
     */
    List<UserResponse> getClassStudents(Long classId);

    /**
     * 批量导入学生到班级
     */
    ImportResultDto importStudentsToClass(Long classId, StudentImportDto studentImportDto);

    /**
     * 获取班级助教列表（包含详细信息）
     */
    List<UserResponse> getClassTeachingAssistants(Long classId);

    /**
     * 添加助教到班级并返回结果
     */
    ImportResultDto addTeachingAssistantsToClass(Long classId, TAImportDto taImportDto);

    /**
     * 学生加入班级
     */
    void enrollStudentInClass(Long classId, String studentId);

    /**
     * 获取学生在某课程中的班级信息
     */
    ClassDto getStudentClassInCourse(String studentId, Long courseId);

    /**
     * 获取学生加入的所有班级信息
     */
    List<StudentClassInfoDto> getStudentEnrolledClassInfo(String studentId);

    /**
     * 获取班级可见的资料列表
     */
    List<MaterialDto> getClassMaterials(Long classId);

    /**
     * 获取学生在班级中的作业完成摘要
     */
    StudentAssignmentSummaryDto getStudentAssignmentSummary(Long classId, String studentId);

}
