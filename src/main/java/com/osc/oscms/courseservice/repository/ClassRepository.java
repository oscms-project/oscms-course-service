package com.osc.oscms.courseservice.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.courseservice.domain.ClassEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 班级数据访问层
 */
@Mapper
public interface ClassRepository extends BaseMapper<ClassEntity> {

        /**
         * 根据课程ID查询班级列表
         */
        @Select("SELECT * FROM osc_class WHERE course_id = #{courseId} ORDER BY created_at ASC")
        List<ClassEntity> findByCourseId(@Param("courseId") Long courseId);

        /**
         * 根据班级代码查询班级
         */
        @Select("SELECT * FROM osc_class WHERE code = #{code}")
        ClassEntity findByCode(@Param("code") String code);

        /**
         * 根据学生ID查询所在班级
         */
        @Select("SELECT c.* FROM osc_class c " +
                        "INNER JOIN osc_class_student cs ON c.id = cs.class_id " +
                        "WHERE cs.student_id = #{studentId}")
        List<ClassEntity> findByStudentId(@Param("studentId") String studentId);

        /**
         * 根据助教ID查询管理的班级
         */
        @Select("SELECT c.* FROM osc_class c " +
                        "INNER JOIN osc_class_ta ct ON c.id = ct.class_id " +
                        "WHERE ct.ta_id = #{taId}")
        List<ClassEntity> findByTaId(@Param("taId") String taId);

        /**
         * 根据班级ID列表查询对应的课程ID
         */
        @Select("<script>" +
                        "SELECT DISTINCT course_id FROM osc_class WHERE id IN " +
                        "<foreach item='classId' collection='classIds' open='(' separator=',' close=')'>" +
                        "#{classId}" +
                        "</foreach>" +
                        "</script>")
        List<Long> getCourseIdsByClassIds(@Param("classIds") List<Long> classIds);
}
