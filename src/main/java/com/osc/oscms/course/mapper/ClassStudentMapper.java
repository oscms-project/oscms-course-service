package com.osc.oscms.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.course.domain.ClassStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 班级学生关联Mapper接口
 */
@Mapper
public interface ClassStudentMapper extends BaseMapper<ClassStudent> {

    /**
     * 根据班级ID查询学生列表
     */
    @Select("SELECT * FROM class_students WHERE class_id = #{classId} AND status = 'ACTIVE'")
    List<ClassStudent> findByClassId(@Param("classId") Long classId);

    /**
     * 根据学生ID查询班级列表
     */
    @Select("SELECT * FROM class_students WHERE student_id = #{studentId} AND status = 'ACTIVE'")
    List<ClassStudent> findByStudentId(@Param("studentId") Long studentId);

    /**
     * 检查学生是否已在班级中
     */
    @Select("SELECT COUNT(*) FROM class_students WHERE class_id = #{classId} AND student_id = #{studentId} AND status = 'ACTIVE'")
    int countByClassIdAndStudentId(@Param("classId") Long classId, @Param("studentId") Long studentId);

    /**
     * 统计班级当前学生数
     */
    @Select("SELECT COUNT(*) FROM class_students WHERE class_id = #{classId} AND status = 'ACTIVE'")
    int countActiveStudentsByClassId(@Param("classId") Long classId);
}



