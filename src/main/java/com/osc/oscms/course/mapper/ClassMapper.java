package com.osc.oscms.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.course.domain.ClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 班级Mapper接口
 */
@Mapper
public interface ClassMapper extends BaseMapper<ClassEntity> {

    /**
     * 根据课程ID查询班级列表
     */
    @Select("SELECT * FROM classes WHERE course_id = #{courseId} AND status != 'DELETED'")
    List<ClassEntity> findByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据教师ID查询班级列表
     */
    @Select("SELECT * FROM classes WHERE instructor_id = #{instructorId} AND status != 'DELETED'")
    List<ClassEntity> findByInstructorId(@Param("instructorId") Long instructorId);

    /**
     * 根据学期和年份查询班级列表
     */
    @Select("SELECT * FROM classes WHERE semester = #{semester} AND year = #{year} AND status != 'DELETED'")
    List<ClassEntity> findBySemesterAndYear(@Param("semester") String semester, @Param("year") Integer year);

    /**
     * 更新班级当前学生数
     */
    @Update("UPDATE classes SET current_students = #{currentStudents} WHERE id = #{classId}")
    int updateCurrentStudents(@Param("classId") Long classId, @Param("currentStudents") Integer currentStudents);
}



