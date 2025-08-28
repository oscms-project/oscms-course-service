package com.osc.oscms.courseservice.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.courseservice.domain.ClassStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 班级学生关联数据访问层
 */
@Mapper
public interface ClassStudentRepository extends BaseMapper<ClassStudent> {
    
    /**
     * 根据班级ID查询学生列表
     */
    @Select("SELECT * FROM osc_class_student WHERE class_id = #{classId}")
    List<ClassStudent> findByClassId(@Param("classId") Long classId);
    
    /**
     * 根据学生ID查询班级列表
     */
    @Select("SELECT * FROM osc_class_student WHERE student_id = #{studentId}")
    List<ClassStudent> findByStudentId(@Param("studentId") String studentId);
    
    /**
     * 检查学生是否在班级中
     */
    @Select("SELECT COUNT(*) FROM osc_class_student WHERE class_id = #{classId} AND student_id = #{studentId}")
    int existsByClassIdAndStudentId(@Param("classId") Long classId, @Param("studentId") String studentId);
    
    /**
     * 删除学生的班级关联
     */
    @Delete("DELETE FROM osc_class_student WHERE class_id = #{classId} AND student_id = #{studentId}")
    int deleteByClassIdAndStudentId(@Param("classId") Long classId, @Param("studentId") String studentId);
    
    /**
     * 删除班级的所有学生关联
     */
    @Delete("DELETE FROM osc_class_student WHERE class_id = #{classId}")
    int deleteByClassId(@Param("classId") Long classId);
    
    /**
     * 统计班级学生数量
     */
    @Select("SELECT COUNT(*) FROM osc_class_student WHERE class_id = #{classId}")
    int countByClassId(@Param("classId") Long classId);
    
    /**
     * 查询班级的所有学生ID
     */
    @Select("SELECT student_id FROM osc_class_student WHERE class_id = #{classId}")
    List<String> findStudentIdsByClassId(@Param("classId") Long classId);
}
