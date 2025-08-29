-- 创建课程资料表
CREATE TABLE IF NOT EXISTS osc_material (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id BIGINT NOT NULL COMMENT '所属课程ID',
    filename VARCHAR(255) NOT NULL COMMENT '文件名',
    original_filename VARCHAR(255) NOT NULL COMMENT '原始文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件存储路径',
    file_size BIGINT NOT NULL COMMENT '文件大小（字节）',
    content_type VARCHAR(100) NOT NULL COMMENT '文件MIME类型',
    type VARCHAR(50) NOT NULL COMMENT '资料类型：document, video, audio, image, other',
    chapter_order INT DEFAULT 0 COMMENT '章节顺序',
    description TEXT COMMENT '资料描述',
    visible_for_classes JSON COMMENT '可见班级ID列表（JSON格式）',
    version INT DEFAULT 1 COMMENT '版本号',
    latest_version INT DEFAULT 1 COMMENT '最新版本号',
    upload_user_id VARCHAR(50) NOT NULL COMMENT '上传用户ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (course_id) REFERENCES osc_course(id) ON DELETE CASCADE,
    INDEX idx_course_id (course_id),
    INDEX idx_type (type),
    INDEX idx_chapter_order (chapter_order),
    INDEX idx_upload_user (upload_user_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程资料表';

-- 创建资料访问记录表（可选，用于统计下载次数等）
CREATE TABLE IF NOT EXISTS osc_material_access (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    material_id BIGINT NOT NULL COMMENT '资料ID',
    user_id VARCHAR(50) NOT NULL COMMENT '访问用户ID',
    access_type VARCHAR(20) NOT NULL COMMENT '访问类型：view, download',
    ip_address VARCHAR(45) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    accessed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (material_id) REFERENCES osc_material(id) ON DELETE CASCADE,
    INDEX idx_material_id (material_id),
    INDEX idx_user_id (user_id),
    INDEX idx_access_type (access_type),
    INDEX idx_accessed_at (accessed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资料访问记录表';

