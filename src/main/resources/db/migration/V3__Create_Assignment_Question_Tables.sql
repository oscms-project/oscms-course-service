-- 创建作业表
CREATE TABLE IF NOT EXISTS assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL COMMENT '作业标题',
    description TEXT COMMENT '作业描述',
    course_id BIGINT NOT NULL COMMENT '所属课程ID',
    class_id BIGINT NOT NULL COMMENT '所属班级ID',
    open_time DATETIME NOT NULL COMMENT '开放时间',
    due_date DATETIME NOT NULL COMMENT '截止时间',
    allow_resubmit BOOLEAN DEFAULT FALSE COMMENT '是否允许重复提交',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_course_id (course_id),
    INDEX idx_class_id (class_id),
    INDEX idx_due_date (due_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作业表';

-- 创建题目表
CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT '题目标题',
    type VARCHAR(50) NOT NULL COMMENT '题目类型：choice, coding, essay',
    choices JSON COMMENT '选择题选项（JSON格式）',
    correct_answer TEXT COMMENT '正确答案',
    score INT DEFAULT 0 COMMENT '题目分数',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_type (type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目表';

-- 创建作业题目关联表
CREATE TABLE IF NOT EXISTS assignment_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assignment_id BIGINT NOT NULL COMMENT '作业ID',
    question_id BIGINT NOT NULL COMMENT '题目ID',
    question_order INT DEFAULT 0 COMMENT '题目在作业中的顺序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_assignment_question (assignment_id, question_id),
    INDEX idx_assignment_id (assignment_id),
    INDEX idx_question_id (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='作业题目关联表';

-- 插入一些示例数据
INSERT INTO questions (title, type, choices, correct_answer, score) VALUES
('Java中的基本数据类型有哪些？', 'choice', 
 '["int, double, boolean, char", "String, int, double", "int, float, string, boolean", "byte, short, int, long"]',
 'int, double, boolean, char', 5),
('实现一个简单的快速排序算法', 'coding', NULL,
 '参考答案：使用分治法实现快速排序', 20),
('简述Java面向对象的三大特性', 'essay', NULL,
 '封装、继承、多态', 10),
('Java中String和StringBuilder的区别', 'essay', NULL,
 'String是不可变的，StringBuilder是可变的', 8),
('以下哪个是Java的关键字？', 'choice',
 '["class", "method", "variable", "function"]',
 'class', 3);
