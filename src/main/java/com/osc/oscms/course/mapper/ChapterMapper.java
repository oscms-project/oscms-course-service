package com.osc.oscms.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.course.domain.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 章节Mapper接口
 */
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

    /**
     * 根据课程ID查询章节列表
     */
    @Select("SELECT * FROM chapters WHERE course_id = #{courseId} AND status != 'DELETED' ORDER BY order_num ASC")
    List<Chapter> findByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据课程ID和状态查询章节列表
     */
    @Select("SELECT * FROM chapters WHERE course_id = #{courseId} AND status = #{status} ORDER BY order_num ASC")
    List<Chapter> findByCourseIdAndStatus(@Param("courseId") Long courseId, @Param("status") String status);

    /**
     * 获取课程中最大的排序号
     */
    @Select("SELECT COALESCE(MAX(order_num), 0) FROM chapters WHERE course_id = #{courseId}")
    Integer getMaxOrderNumByCourseId(@Param("courseId") Long courseId);
}



