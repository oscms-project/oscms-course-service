package com.osc.oscms.courseservice.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.courseservice.domain.ClassTA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 班级助教关联数据访问层
 */
@Mapper
public interface ClassTARepository extends BaseMapper<ClassTA> {
    
    /**
     * 根据班级ID查询助教列表
     */
    @Select("SELECT * FROM osc_class_ta WHERE class_id = #{classId}")
    List<ClassTA> findByClassId(@Param("classId") Long classId);
    
    /**
     * 根据助教ID查询班级列表
     */
    @Select("SELECT * FROM osc_class_ta WHERE ta_id = #{taId}")
    List<ClassTA> findByTaId(@Param("taId") String taId);
    
    /**
     * 检查助教是否在班级中
     */
    @Select("SELECT COUNT(*) FROM osc_class_ta WHERE class_id = #{classId} AND ta_id = #{taId}")
    int existsByClassIdAndTaId(@Param("classId") Long classId, @Param("taId") String taId);
    
    /**
     * 删除助教的班级关联
     */
    @Delete("DELETE FROM osc_class_ta WHERE class_id = #{classId} AND ta_id = #{taId}")
    int deleteByClassIdAndTaId(@Param("classId") Long classId, @Param("taId") String taId);
    
    /**
     * 删除班级的所有助教关联
     */
    @Delete("DELETE FROM osc_class_ta WHERE class_id = #{classId}")
    int deleteByClassId(@Param("classId") Long classId);
    
    /**
     * 查询班级的所有助教ID
     */
    @Select("SELECT ta_id FROM osc_class_ta WHERE class_id = #{classId}")
    List<String> findTaIdsByClassId(@Param("classId") Long classId);
}
