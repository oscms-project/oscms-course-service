package com.osc.oscms.courseservice.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.courseservice.domain.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 章节数据访问层
 */
@Mapper
public interface ChapterRepository extends BaseMapper<Chapter> {

    /**
     * 根据课程ID查询章节列表
     */
    @Select("SELECT * FROM osc_chapter WHERE course_id = #{courseId} ORDER BY `order` ASC")
    List<Chapter> findByCourseId(@Param("courseId") Long courseId);

    /**
     * 获取课程的最大章节顺序号
     */
    @Select("SELECT COALESCE(MAX(`order`), 0) FROM osc_chapter WHERE course_id = #{courseId}")
    Integer getMaxOrderByCourseId(@Param("courseId") Long courseId);

    /**
     * 删除课程的所有章节
     */
    @Delete("DELETE FROM osc_chapter WHERE course_id = #{courseId}")
    int deleteByCourseId(@Param("courseId") Long courseId);
}
