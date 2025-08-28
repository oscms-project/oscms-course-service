-- Create course management tables
CREATE TABLE IF NOT EXISTS osc_course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '课程名称',
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '课程代码',
    outline TEXT COMMENT '课程大纲',
    objectives TEXT COMMENT '课程目标',
    assessment TEXT COMMENT '考核方式',
    teacher_id VARCHAR(50) NOT NULL COMMENT '教师ID',
    completed BOOLEAN DEFAULT FALSE COMMENT '是否结课',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

CREATE TABLE IF NOT EXISTS osc_chapter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL COMMENT '课程ID',
    title VARCHAR(200) NOT NULL COMMENT '章节标题',
    `order` INT NOT NULL COMMENT '章节顺序',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES osc_course(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='章节表';

CREATE TABLE IF NOT EXISTS osc_class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '班级名称',
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '班级代码',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES osc_course(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级表';

CREATE TABLE IF NOT EXISTS osc_class_student (
    class_id BIGINT NOT NULL,
    student_id VARCHAR(50) NOT NULL,
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (class_id, student_id),
    FOREIGN KEY (class_id) REFERENCES osc_class(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级学生关联表';

CREATE TABLE IF NOT EXISTS osc_class_ta (
    class_id BIGINT NOT NULL,
    ta_id VARCHAR(50) NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (class_id, ta_id),
    FOREIGN KEY (class_id) REFERENCES osc_class(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级助教关联表';

-- Create indexes
CREATE INDEX idx_course_teacher ON osc_course(teacher_id);
CREATE INDEX idx_chapter_course ON osc_chapter(course_id);
CREATE INDEX idx_class_course ON osc_class(course_id);
CREATE INDEX idx_class_student_student ON osc_class_student(student_id);
CREATE INDEX idx_class_ta_ta ON osc_class_ta(ta_id);
