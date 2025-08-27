-- 课程表
CREATE TABLE osc_course (
                            id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
                            name        VARCHAR(128) NOT NULL,
                            code        VARCHAR(64)  NOT NULL UNIQUE,
                            outline     TEXT,
                            objectives  TEXT,
                            assessment  TEXT,
                            teacher_id  VARCHAR(50)  NOT NULL,
                            completed   TINYINT(1)   DEFAULT 0,
                            created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    -- 课程表的外键 fk_course_teacher 依赖 users 表，
    -- 但在微服务中，不建议设置跨数据库的外键约束。
    -- 数据的关联和一致性应由应用层（服务间调用）来保证。
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 章节表
CREATE TABLE osc_chapter (
                             id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
                             course_id   BIGINT       NOT NULL,
                             title       VARCHAR(256) NOT NULL,
                             `order`     INT          NOT NULL,
                             created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             CONSTRAINT fk_chapter_course FOREIGN KEY (course_id) REFERENCES osc_course(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 班级表
CREATE TABLE osc_class (
                           id          BIGINT       PRIMARY KEY AUTO_INCREMENT,
                           name        VARCHAR(128) NOT NULL,
                           code        VARCHAR(64)  NOT NULL UNIQUE,
                           course_id   BIGINT       NOT NULL,
                           created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           CONSTRAINT fk_class_course FOREIGN KEY (course_id) REFERENCES osc_course(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 班级-学生关联表
CREATE TABLE osc_class_student (
                                   class_id    BIGINT NOT NULL,
                                   student_id  VARCHAR(50) NOT NULL,
                                   enrolled_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (class_id, student_id),
                                   CONSTRAINT fk_cs_class FOREIGN KEY (class_id) REFERENCES osc_class(id) ON DELETE CASCADE
    -- 移除了对 users 表的外键约束
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 班级-助教关联表
CREATE TABLE osc_class_ta (
                              class_id    BIGINT       NOT NULL,
                              ta_id       VARCHAR(50)  NOT NULL,
                              assigned_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (class_id, ta_id),
                              CONSTRAINT fk_cta_class FOREIGN KEY (class_id) REFERENCES osc_class(id) ON DELETE CASCADE
    -- 移除了对 users 表的外键约束
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;