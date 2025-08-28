package com.osc.oscms.course.service.impl;

import com.osc.oscms.course.dto.*;
import com.osc.oscms.course.domain.ClassEntity;
import com.osc.oscms.course.domain.ClassStudent;
import com.osc.oscms.course.domain.ClassTA;
import com.osc.oscms.course.mapper.ClassMapper;
import com.osc.oscms.course.mapper.ClassStudentMapper;
import com.osc.oscms.course.mapper.ClassTAMapper;
import com.osc.oscms.course.service.ClassService;
import com.osc.oscms.common.exception.BusinessException;
import com.osc.oscms.common.response.ResponseCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班级服务实现类
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private ClassStudentMapper classStudentMapper;

    @Autowired
    private ClassTAMapper classTAMapper;

    @Override
    public ClassResponse createClass(ClassCreateRequest request) {
        ClassEntity classEntity = new ClassEntity();
        BeanUtils.copyProperties(request, classEntity);
        classEntity.setCurrentStudents(0); // 初始学生数为0
        classEntity.setCreatedAt(LocalDateTime.now());
        classEntity.setUpdatedAt(LocalDateTime.now());

        int result = classMapper.insert(classEntity);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "创建班级失败");
        }

        return convertToResponse(classEntity);
    }

    @Override
    public ClassResponse updateClass(Long id, ClassUpdateRequest request) {
        ClassEntity classEntity = classMapper.selectById(id);
        if (classEntity == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "班级不存在");
        }

        // 只更新非空字段
        if (request.getName() != null) {
            classEntity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            classEntity.setDescription(request.getDescription());
        }
        if (request.getInstructorId() != null) {
            classEntity.setInstructorId(request.getInstructorId());
        }
        if (request.getMaxStudents() != null) {
            classEntity.setMaxStudents(request.getMaxStudents());
        }
        if (request.getStatus() != null) {
            classEntity.setStatus(request.getStatus());
        }
        if (request.getSemester() != null) {
            classEntity.setSemester(request.getSemester());
        }
        if (request.getYear() != null) {
            classEntity.setYear(request.getYear());
        }
        classEntity.setUpdatedAt(LocalDateTime.now());

        int result = classMapper.updateById(classEntity);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "更新班级失败");
        }

        return convertToResponse(classEntity);
    }

    @Override
    public void deleteClass(Long id) {
        ClassEntity classEntity = classMapper.selectById(id);
        if (classEntity == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "班级不存在");
        }

        classEntity.setStatus("DELETED");
        classEntity.setUpdatedAt(LocalDateTime.now());

        int result = classMapper.updateById(classEntity);
        if (result <= 0) {
            throw new BusinessException(ResponseCode.OPERATION_FAILED, "删除班级失败");
        }
    }

    @Override
    public ClassResponse getClassById(Long id) {
        ClassEntity classEntity = classMapper.selectById(id);
        if (classEntity == null || "DELETED".equals(classEntity.getStatus())) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "班级不存在");
        }

        return convertToResponse(classEntity);
    }

    @Override
    public List<ClassResponse> getClassesByCourseId(Long courseId) {
        List<ClassEntity> classes = classMapper.findByCourseId(courseId);
        return classes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassResponse> getClassesByInstructorId(Long instructorId) {
        List<ClassEntity> classes = classMapper.findByInstructorId(instructorId);
        return classes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassResponse> getClassesBySemesterAndYear(String semester, Integer year) {
        List<ClassEntity> classes = classMapper.findBySemesterAndYear(semester, year);
        return classes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addStudentToClass(Long classId, Long studentId, String studentName, String studentEmail) {
        // 检查班级是否存在
        ClassEntity classEntity = classMapper.selectById(classId);
        if (classEntity == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "班级不存在");
        }

        // 检查学生是否已在班级中
        int count = classStudentMapper.countByClassIdAndStudentId(classId, studentId);
        if (count > 0) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "学生已在班级中");
        }

        // 检查班级是否已满
        int currentStudents = classStudentMapper.countActiveStudentsByClassId(classId);
        if (currentStudents >= classEntity.getMaxStudents()) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "班级已满");
        }

        // 添加学生到班级
        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(classId);
        classStudent.setStudentId(studentId);
        classStudent.setStudentName(studentName);
        classStudent.setStudentEmail(studentEmail);
        classStudent.setStatus("ACTIVE");
        classStudent.setEnrollmentDate(LocalDateTime.now());
        classStudent.setCreatedAt(LocalDateTime.now());
        classStudent.setUpdatedAt(LocalDateTime.now());

        classStudentMapper.insert(classStudent);

        // 更新班级当前学生数
        classMapper.updateCurrentStudents(classId, currentStudents + 1);

        // 如果班级已满，更新班级状态
        if (currentStudents + 1 >= classEntity.getMaxStudents()) {
            classEntity.setStatus("FULL");
            classMapper.updateById(classEntity);
        }
    }

    @Override
    @Transactional
    public void removeStudentFromClass(Long classId, Long studentId) {
        // 查找学生记录
        List<ClassStudent> classStudents = classStudentMapper.findByClassId(classId);
        ClassStudent targetStudent = classStudents.stream()
                .filter(cs -> cs.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (targetStudent == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "学生不在该班级中");
        }

        // 更新学生状态为DROPPED
        targetStudent.setStatus("DROPPED");
        targetStudent.setUpdatedAt(LocalDateTime.now());
        classStudentMapper.updateById(targetStudent);

        // 更新班级当前学生数
        int currentStudents = classStudentMapper.countActiveStudentsByClassId(classId);
        classMapper.updateCurrentStudents(classId, currentStudents);

        // 如果班级不再满员，更新状态
        ClassEntity classEntity = classMapper.selectById(classId);
        if ("FULL".equals(classEntity.getStatus()) && currentStudents < classEntity.getMaxStudents()) {
            classEntity.setStatus("ACTIVE");
            classMapper.updateById(classEntity);
        }
    }

    @Override
    public void assignTAToClass(Long classId, Long taId, String taName, String taEmail) {
        // 检查班级是否存在
        ClassEntity classEntity = classMapper.selectById(classId);
        if (classEntity == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "班级不存在");
        }

        // 检查助教是否已在班级中
        int count = classTAMapper.countByClassIdAndTAId(classId, taId);
        if (count > 0) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "助教已在班级中");
        }

        // 添加助教到班级
        ClassTA classTA = new ClassTA();
        classTA.setClassId(classId);
        classTA.setTaId(taId);
        classTA.setTaName(taName);
        classTA.setTaEmail(taEmail);
        classTA.setStatus("ACTIVE");
        classTA.setAssignedDate(LocalDateTime.now());
        classTA.setCreatedAt(LocalDateTime.now());
        classTA.setUpdatedAt(LocalDateTime.now());

        classTAMapper.insert(classTA);
    }

    @Override
    public void removeTAFromClass(Long classId, Long taId) {
        // 查找助教记录
        List<ClassTA> classTAs = classTAMapper.findByClassId(classId);
        ClassTA targetTA = classTAs.stream()
                .filter(ct -> ct.getTaId().equals(taId))
                .findFirst()
                .orElse(null);

        if (targetTA == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "助教不在该班级中");
        }

        // 更新助教状态为INACTIVE
        targetTA.setStatus("INACTIVE");
        targetTA.setUpdatedAt(LocalDateTime.now());
        classTAMapper.updateById(targetTA);
    }

    @Override
    public List<Object> getClassStudents(Long classId) {
        List<ClassStudent> classStudents = classStudentMapper.findByClassId(classId);
        return classStudents.stream()
                .map(cs -> (Object) cs)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object> getClassTAs(Long classId) {
        List<ClassTA> classTAs = classTAMapper.findByClassId(classId);
        return classTAs.stream()
                .map(ct -> (Object) ct)
                .collect(Collectors.toList());
    }

    private ClassResponse convertToResponse(ClassEntity classEntity) {
        ClassResponse response = new ClassResponse();
        BeanUtils.copyProperties(classEntity, response);
        return response;
    }
}



