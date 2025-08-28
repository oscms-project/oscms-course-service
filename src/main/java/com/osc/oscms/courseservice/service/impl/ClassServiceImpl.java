package com.osc.oscms.courseservice.service.impl;


import com.osc.oscms.courseservice.domain.ClassEntity;
import com.osc.oscms.courseservice.domain.ClassStudent;
import com.osc.oscms.courseservice.domain.ClassTA;
import com.osc.oscms.courseservice.dto.ClassCreateDto;
import com.osc.oscms.courseservice.dto.ClassDto;
import com.osc.oscms.courseservice.repository.ClassRepository;
import com.osc.oscms.courseservice.repository.ClassStudentRepository;
import com.osc.oscms.courseservice.repository.ClassTARepository;
import com.osc.oscms.courseservice.service.ClassService;
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
            throw new BusinessException("班级代码已存在");
        }
        
        ClassEntity classEntity = new ClassEntity();
        BeanUtils.copyProperties(classCreateDto, classEntity);
        classEntity.setCreatedAt(LocalDateTime.now());
        classEntity.setUpdatedAt(LocalDateTime.now());
        
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
                throw new BusinessException("班级代码已存在");
            }
            classEntity.setCode(classDto.getCode());
        }
        
        classEntity.setUpdatedAt(LocalDateTime.now());
        
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
            throw new BusinessException("学生已在该班级中");
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
            throw new BusinessException("助教已在该班级中");
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
            throw new BusinessException("班级不存在");
        }
        return classEntity;
    }
    
    private ClassDto convertToDto(ClassEntity classEntity) {
        ClassDto dto = new ClassDto();
        BeanUtils.copyProperties(classEntity, dto);
        return dto;
    }
}