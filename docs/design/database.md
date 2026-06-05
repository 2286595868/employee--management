# Database Design

## 一、数据库规范

1. 数据库使用 MySQL 8。
2. 表名使用小写下划线。
3. 字段名使用小写下划线。
4. 每张业务表必须有 id、create_time、update_time。
5. 删除使用逻辑删除，不做物理删除。
6. 逻辑删除字段统一为 deleted。
7. 字符集使用 utf8mb4。
8. 不使用数据库外键，关联关系由业务代码控制。
9. 不允许代码中使用本文档没有定义的字段。

## 二、employee 员工表

员工表用于保存员工基础信息。

SQL：

CREATE TABLE employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '员工ID',
    employee_no VARCHAR(32) NOT NULL COMMENT '员工编号',
    name VARCHAR(50) NOT NULL COMMENT '员工姓名',
    gender TINYINT NOT NULL DEFAULT 0 COMMENT '性别：0未知，1男，2女',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    department_id BIGINT DEFAULT NULL COMMENT '部门ID',
    position VARCHAR(50) DEFAULT NULL COMMENT '职位',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1在职，0离职',
    hire_date DATE DEFAULT NULL COMMENT '入职日期',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0否，1是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_employee_no (employee_no),
    KEY idx_department_id (department_id),
    KEY idx_name (name),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

## 三、department 部门表

部门表用于保存部门信息。

SQL：

CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    name VARCHAR(50) NOT NULL COMMENT '部门名称',
    parent_id BIGINT DEFAULT NULL COMMENT '父部门ID',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0禁用',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除：0否，1是',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

## 四、字段枚举

employee.gender：

- 0：未知
- 1：男
- 2：女

employee.status：

- 1：在职
- 0：离职

department.status：

- 1：启用
- 0：禁用

deleted：

- 0：未删除
- 1：已删除

## 五、数据库修改规则

1. 修改表结构前，必须先更新本文档。
2. 修改字段含义前，必须同步更新 docs/specs/api.md。
3. 删除字段必须说明原因。
4. 不允许代码中使用本文档不存在的字段。
