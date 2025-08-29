package com.osc.oscms.courseservice.client;

import com.osc.oscms.common.dto.material.MaterialDto;
import com.osc.oscms.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 资料服务Feign客户端
 * 用于课程服务调用资料服务获取资料信息
 */
@FeignClient(name = "oscms-material-service")
public interface MaterialServiceClient {

        /**
         * 获取指定班级可见的资料列表
         * 根据班级ID和对应的课程ID，获取该班级可见的所有资料
         */
        @GetMapping("/materials/course/{courseId}/class/{classId}")
        ApiResponse<List<MaterialDto>> getClassMaterials(
                        @PathVariable("courseId") Long courseId,
                        @PathVariable("classId") Long classId);

        /**
         * 获取课程的所有资料
         */
        @GetMapping("/courses/{courseId}/materials")
        ApiResponse<List<MaterialDto>> getCourseMaterials(@PathVariable("courseId") Long courseId);

        /**
         * 获取课程的资料，可按类型和章节过滤
         */
        @GetMapping("/courses/{courseId}/materials/filter")
        ApiResponse<List<MaterialDto>> getCourseMaterialsFiltered(
                        @PathVariable("courseId") Long courseId,
                        @RequestParam(required = false) String type,
                        @RequestParam(required = false) Integer chapterOrder);
}
