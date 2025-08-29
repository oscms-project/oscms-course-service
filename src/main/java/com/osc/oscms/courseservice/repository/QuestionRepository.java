package com.osc.oscms.courseservice.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osc.oscms.courseservice.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 题目数据访问层
 */
@Mapper
public interface QuestionRepository extends BaseMapper<Question> {

    /**
     * 根据题目ID列表查找题目
     */
    @Select("<script>" +
            "SELECT * FROM questions WHERE id IN " +
            "<foreach item='id' collection='questionIds' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " ORDER BY id" +
            "</script>")
    List<Question> findByIds(List<Long> questionIds);

    /**
     * 根据类型查找题目
     */
    @Select("SELECT * FROM questions WHERE type = #{type} ORDER BY created_at DESC")
    List<Question> findByType(String type);

    /**
     * 搜索题目（根据标题）
     */
    @Select("SELECT * FROM questions WHERE title LIKE CONCAT('%', #{keyword}, '%') ORDER BY created_at DESC")
    List<Question> searchByTitle(String keyword);
}
