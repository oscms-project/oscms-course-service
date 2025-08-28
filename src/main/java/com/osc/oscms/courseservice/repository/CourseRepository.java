package com.osc.oscms.courseservice.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.courseservice.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 课程数据访问层
 */
@Mapper
public interface CourseRepository extends BaseMapper<Course> {
    
    /**
     * 根据教师ID查询课程列表
     */
    @Select("SELECT * FROM osc_course WHERE teacher_id = #{teacherId} ORDER BY created_at DESC")
    List<Course> findByTeacherId(@Param("teacherId") String teacherId);
    
    /**
     * 根据课程代码查询课程
     */
    @Select("SELECT * FROM osc_course WHERE code = #{code}")
    Course findByCode(@Param("code") String code);
    
    /**
     * 查询所有未结课的课程
     */
    @Select("SELECT * FROM osc_course WHERE completed = false ORDER BY created_at DESC")
    List<Course> findActiveCourses();
    
    /**
     * 查询已结课的课程
     */
    @Select("SELECT * FROM osc_course WHERE completed = true ORDER BY updated_at DESC")
    List<Course> findCompletedCourses();
    
    /**
     * 根据课程名称模糊搜索
     */
    @Select("SELECT * FROM osc_course WHERE name LIKE CONCAT('%', #{keyword}, '%') ORDER BY created_at DESC")
    List<Course> searchByName(@Param("keyword") String keyword);
}
