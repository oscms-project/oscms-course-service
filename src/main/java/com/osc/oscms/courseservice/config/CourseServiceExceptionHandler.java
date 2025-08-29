package com.osc.oscms.courseservice.config;

import com.osc.oscms.common.exception.BusinessException;
import com.osc.oscms.common.exception.CourseException.*;
import com.osc.oscms.common.exception.ClassException.*;
import com.osc.oscms.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程服务异常处理器
 */
@Slf4j
@RestControllerAdvice
public class CourseServiceExceptionHandler {

        /**
         * 处理课程相关异常
         */
        @ExceptionHandler(CourseNotFoundException.class)
        public ResponseEntity<ApiResponse<Void>> handleCourseNotFoundException(CourseNotFoundException e) {
                log.warn("Course not found: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.error(404, e.getMessage()));
        }

        @ExceptionHandler(CourseCodeAlreadyExistsException.class)
        public ResponseEntity<ApiResponse<Void>> handleCourseCodeAlreadyExistsException(
                        CourseCodeAlreadyExistsException e) {
                log.warn("Course code already exists: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error(400, e.getMessage()));
        }

        @ExceptionHandler(InvalidCourseDataException.class)
        public ResponseEntity<ApiResponse<Void>> handleInvalidCourseDataException(InvalidCourseDataException e) {
                log.warn("Invalid course data: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error(400, e.getMessage()));
        }

        /**
         * 处理班级相关异常
         */
        @ExceptionHandler(ClazzNotFoundException.class)
        public ResponseEntity<ApiResponse<Void>> handleClazzNotFoundException(ClazzNotFoundException e) {
                log.warn("Class not found: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.error(404, e.getMessage()));
        }

        @ExceptionHandler(InvalidClassDataException.class)
        public ResponseEntity<ApiResponse<Void>> handleInvalidClassDataException(InvalidClassDataException e) {
                log.warn("Invalid class data: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error(400, e.getMessage()));
        }

        @ExceptionHandler(DuplicateStudentInClassException.class)
        public ResponseEntity<ApiResponse<Void>> handleDuplicateStudentInClassException(
                        DuplicateStudentInClassException e) {
                log.warn("Duplicate student in class: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error(400, e.getMessage()));
        }

        @ExceptionHandler(DuplicateTAInClassException.class)
        public ResponseEntity<ApiResponse<Void>> handleDuplicateTAInClassException(DuplicateTAInClassException e) {
                log.warn("Duplicate TA in class: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error(400, e.getMessage()));
        }
}