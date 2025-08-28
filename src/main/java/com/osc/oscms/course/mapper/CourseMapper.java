package com.osc.oscms.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.course.domain.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据教师ID查询课程列表
     */
    @Select("SELECT * FROM courses WHERE instructor_id = #{instructorId} AND status != 'DELETED'")
    List<Course> findByInstructorId(@Param("instructorId") Long instructorId);

    /**
     * 根据状态查询课程列表
     */
    @Select("SELECT * FROM courses WHERE status = #{status}")
    List<Course> findByStatus(@Param("status") String status);

    /**
     * 搜索课程（按名称或描述）
     */
    @Select("SELECT * FROM courses WHERE (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')) AND status != 'DELETED'")
    List<Course> searchCourses(@Param("keyword") String keyword);
}



