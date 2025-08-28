package com.osc.oscms.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.course.domain.ClassTA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 班级助教关联Mapper接口
 */
@Mapper
public interface ClassTAMapper extends BaseMapper<ClassTA> {

    /**
     * 根据班级ID查询助教列表
     */
    @Select("SELECT * FROM class_tas WHERE class_id = #{classId} AND status = 'ACTIVE'")
    List<ClassTA> findByClassId(@Param("classId") Long classId);

    /**
     * 根据助教ID查询班级列表
     */
    @Select("SELECT * FROM class_tas WHERE ta_id = #{taId} AND status = 'ACTIVE'")
    List<ClassTA> findByTAId(@Param("taId") Long taId);

    /**
     * 检查助教是否已在班级中
     */
    @Select("SELECT COUNT(*) FROM class_tas WHERE class_id = #{classId} AND ta_id = #{taId} AND status = 'ACTIVE'")
    int countByClassIdAndTAId(@Param("classId") Long classId, @Param("taId") Long taId);
}



