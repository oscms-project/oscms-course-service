package com.osc.oscms.courseservice.service.impl;

import com.osc.oscms.courseservice.domain.ClassEntity;
import com.osc.oscms.courseservice.domain.ClassStudent;
import com.osc.oscms.courseservice.domain.ClassTA;
import com.osc.oscms.common.dto.clazz.ClassCreateDto;
import com.osc.oscms.common.dto.clazz.ClassDto;
import com.osc.oscms.common.dto.clazz.StudentImportDto;
import com.osc.oscms.common.dto.clazz.TAImportDto;

import com.osc.oscms.common.dto.common.ImportResultDto;
import com.osc.oscms.common.dto.user.UserResponse;

import com.osc.oscms.courseservice.repository.ClassRepository;
import com.osc.oscms.courseservice.repository.ClassStudentRepository;
import com.osc.oscms.courseservice.repository.ClassTARepository;
import com.osc.oscms.courseservice.service.ClassService;

import com.osc.oscms.common.exception.ClassException.ClazzNotFoundException;
import com.osc.oscms.common.exception.ClassException.DuplicateStudentInClassException;
import com.osc.oscms.common.exception.ClassException.DuplicateTAInClassException;
import com.osc.oscms.common.exception.ClassException.InvalidClassDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班级服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final ClassStudentRepository classStudentRepository;
    private final ClassTARepository classTARepository;

    @Override
    @Transactional
    public ClassDto createClass(ClassCreateDto classCreateDto) {
        log.info("Creating class: {}", classCreateDto.getName());

        // 检查班级代码是否已存在
        if (classRepository.findByCode(classCreateDto.getCode()) != null) {
            throw new InvalidClassDataException("班级代码已存在: " + classCreateDto.getCode());
        }

        ClassEntity classEntity = new ClassEntity();
        BeanUtils.copyProperties(classCreateDto, classEntity);

        classRepository.insert(classEntity);

        return convertToDto(classEntity);
    }

    @Override
    @Transactional
    public ClassDto updateClass(Long classId, ClassDto classDto) {
        log.info("Updating class: {}", classId);

        ClassEntity classEntity = getClassByIdOrThrow(classId);

        // 更新字段
        if (classDto.getName() != null) {
            classEntity.setName(classDto.getName());
        }
        if (classDto.getCode() != null && !classDto.getCode().equals(classEntity.getCode())) {
            // 检查新代码是否已存在
            if (classRepository.findByCode(classDto.getCode()) != null) {
                throw new InvalidClassDataException("班级代码已存在: " + classDto.getCode());
            }
            classEntity.setCode(classDto.getCode());
        }

        classRepository.updateById(classEntity);

        return convertToDto(classEntity);
    }

    @Override
    @Transactional
    public void deleteClass(Long classId) {
        log.info("Deleting class: {}", classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        // 删除班级的所有学生关联
        classStudentRepository.deleteByClassId(classId);

        // 删除班级的所有助教关联
        classTARepository.deleteByClassId(classId);

        // 删除班级
        classRepository.deleteById(classId);
    }

    @Override
    public ClassDto getClassById(Long classId) {
        ClassEntity classEntity = getClassByIdOrThrow(classId);
        return convertToDto(classEntity);
    }

    @Override
    public List<ClassDto> getClassesByCourseId(Long courseId) {
        log.info("Getting classes for course: {}", courseId);

        List<ClassEntity> classes = classRepository.findByCourseId(courseId);
        return classes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDto> getClassesByStudentId(String studentId) {
        log.info("Getting classes for student: {}", studentId);

        List<ClassEntity> classes = classRepository.findByStudentId(studentId);
        return classes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDto> getClassesByTaId(String taId) {
        log.info("Getting classes for TA: {}", taId);

        List<ClassEntity> classes = classRepository.findByTaId(taId);
        return classes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addStudentToClass(Long classId, String studentId) {
        log.info("Adding student {} to class {}", studentId, classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        // 检查学生是否已在班级中
        if (classStudentRepository.existsByClassIdAndStudentId(classId, studentId) > 0) {
            throw new DuplicateStudentInClassException("学生已在该班级中: " + studentId);
        }

        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(classId);
        classStudent.setStudentId(studentId);
        classStudent.setEnrolledAt(LocalDateTime.now());

        classStudentRepository.insert(classStudent);
    }

    @Override
    @Transactional
    public void removeStudentFromClass(Long classId, String studentId) {
        log.info("Removing student {} from class {}", studentId, classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        classStudentRepository.deleteByClassIdAndStudentId(classId, studentId);
    }

    @Override
    @Transactional
    public void addTAToClass(Long classId, String taId) {
        log.info("Adding TA {} to class {}", taId, classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        // 检查助教是否已在班级中
        if (classTARepository.existsByClassIdAndTaId(classId, taId) > 0) {
            throw new DuplicateTAInClassException("助教已在该班级中: " + taId);
        }

        ClassTA classTA = new ClassTA();
        classTA.setClassId(classId);
        classTA.setTaId(taId);
        classTA.setAssignedAt(LocalDateTime.now());

        classTARepository.insert(classTA);
    }

    @Override
    @Transactional
    public void removeTAFromClass(Long classId, String taId) {
        log.info("Removing TA {} from class {}", taId, classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        classTARepository.deleteByClassIdAndTaId(classId, taId);
    }

    @Override
    public List<String> getClassStudentIds(Long classId) {
        log.info("Getting student IDs for class: {}", classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        return classStudentRepository.findStudentIdsByClassId(classId);
    }

    @Override
    public List<String> getClassTAIds(Long classId) {
        log.info("Getting TA IDs for class: {}", classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        return classTARepository.findTaIdsByClassId(classId);
    }

    @Override
    @Transactional
    public void addStudentsToClass(Long classId, List<String> studentIds) {
        log.info("Adding {} students to class {}", studentIds.size(), classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        for (String studentId : studentIds) {
            if (classStudentRepository.existsByClassIdAndStudentId(classId, studentId) == 0) {
                ClassStudent classStudent = new ClassStudent();
                classStudent.setClassId(classId);
                classStudent.setStudentId(studentId);
                classStudent.setEnrolledAt(LocalDateTime.now());

                classStudentRepository.insert(classStudent);
            }
        }
    }

    @Override
    @Transactional
    public void addTAsToClass(Long classId, List<String> taIds) {
        log.info("Adding {} TAs to class {}", taIds.size(), classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        for (String taId : taIds) {
            if (classTARepository.existsByClassIdAndTaId(classId, taId) == 0) {
                ClassTA classTA = new ClassTA();
                classTA.setClassId(classId);
                classTA.setTaId(taId);
                classTA.setAssignedAt(LocalDateTime.now());

                classTARepository.insert(classTA);
            }
        }
    }

    private ClassEntity getClassByIdOrThrow(Long classId) {
        ClassEntity classEntity = classRepository.selectById(classId);
        if (classEntity == null) {
            throw new ClazzNotFoundException("班级未找到: " + classId);
        }
        return classEntity;
    }

    private ClassDto convertToDto(ClassEntity classEntity) {
        ClassDto dto = new ClassDto();
        BeanUtils.copyProperties(classEntity, dto);
        return dto;
    }

    @Override
    public List<ClassDto> getAllClasses() {
        log.info("Getting all classes");

        List<ClassEntity> classes = classRepository.selectList(null);
        return classes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getClassStudents(Long classId) {
        log.info("Getting detailed student info for class: {}", classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        // TODO: 调用用户服务获取学生详细信息
        // 暂时返回空列表，需要集成用户服务后完善
        List<String> studentIds = classStudentRepository.findStudentIdsByClassId(classId);
        return studentIds.stream()
                .map(this::createMockUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ImportResultDto importStudentsToClass(Long classId, StudentImportDto studentImportDto) {
        log.info("Importing {} students to class {}", studentImportDto.getStudentIds().size(), classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        int importedCount = 0;
        for (String studentId : studentImportDto.getStudentIds()) {
            if (classStudentRepository.existsByClassIdAndStudentId(classId, studentId) == 0) {
                ClassStudent classStudent = new ClassStudent();
                classStudent.setClassId(classId);
                classStudent.setStudentId(studentId);
                classStudent.setEnrolledAt(LocalDateTime.now());

                classStudentRepository.insert(classStudent);
                importedCount++;
            }
        }

        ImportResultDto result = new ImportResultDto();
        result.setImportedCount(importedCount);
        return result;
    }

    @Override
    public List<UserResponse> getClassTeachingAssistants(Long classId) {
        log.info("Getting detailed TA info for class: {}", classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        // TODO: 调用用户服务获取助教详细信息
        // 暂时返回空列表，需要集成用户服务后完善
        List<String> taIds = classTARepository.findTaIdsByClassId(classId);
        return taIds.stream()
                .map(this::createMockUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ImportResultDto addTeachingAssistantsToClass(Long classId, TAImportDto taImportDto) {
        log.info("Adding {} TAs to class {}", taImportDto.getTaIds().size(), classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        int importedCount = 0;
        for (String taId : taImportDto.getTaIds()) {
            if (classTARepository.existsByClassIdAndTaId(classId, taId) == 0) {
                ClassTA classTA = new ClassTA();
                classTA.setClassId(classId);
                classTA.setTaId(taId);
                classTA.setAssignedAt(LocalDateTime.now());

                classTARepository.insert(classTA);
                importedCount++;
            }
        }

        ImportResultDto result = new ImportResultDto();
        result.setImportedCount(importedCount);
        return result;
    }

    @Override
    @Transactional
    public void enrollStudentInClass(Long classId, String studentId) {
        log.info("Student {} enrolling in class {}", studentId, classId);

        // 验证班级存在
        getClassByIdOrThrow(classId);

        // 检查学生是否已在班级中
        if (classStudentRepository.existsByClassIdAndStudentId(classId, studentId) > 0) {
            throw new DuplicateStudentInClassException("您已经在该班级中");
        }

        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(classId);
        classStudent.setStudentId(studentId);
        classStudent.setEnrolledAt(LocalDateTime.now());

        classStudentRepository.insert(classStudent);
    }

    /**
     * 创建模拟用户响应对象
     * TODO: 替换为实际的用户服务调用
     */
    private UserResponse createMockUserResponse(String userId) {
        UserResponse user = new UserResponse();
        user.setId(userId);
        user.setUsername("User " + userId);
        user.setRole("ROLE_STUDENT");
        user.setEmail(userId + "@example.com");
        return user;
    }
}