package com.osc.oscms.courseservice.client;

import com.osc.oscms.common.dto.assignment.AssignmentDto;
import com.osc.oscms.common.dto.submission.SubmissionDto;
import com.osc.oscms.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 作业评估服务Feign客户端
 * 用于课程服务调用作业评估服务获取作业和提交信息
 */
@FeignClient(name = "oscms-assessment-service")
public interface AssessmentServiceClient {

    /**
     * 获取班级的所有作业
     */
    @GetMapping("/classes/{classId}/assignments")
    ApiResponse<List<AssignmentDto>> getClassAssignments(@PathVariable("classId") Long classId);

    /**
     * 获取学生在特定作业的提交记录
     */
    @GetMapping("/submissions/assignment/{assignmentId}")
    ApiResponse<List<SubmissionDto>> getAssignmentSubmissions(
            @PathVariable("assignmentId") Long assignmentId,
            @RequestParam(value = "studentId", required = false) String studentId);
}
