# Database Schema Snapshot

来源文件：scripts/init-database.sql

更新时间：2026-06-05

## employee

员工表。

| 字段 | 类型 | 必填 | 默认值 | 说明 |
| --- | --- | --- | --- | --- |
| id | BIGINT | 是 | AUTO_INCREMENT | 员工ID，主键 |
| employee_no | VARCHAR(32) | 是 | 无 | 员工编号，唯一 |
| name | VARCHAR(50) | 是 | 无 | 员工姓名 |
| gender | TINYINT | 是 | 0 | 性别：0未知，1男，2女 |
| phone | VARCHAR(20) | 否 | NULL | 手机号 |
| email | VARCHAR(100) | 否 | NULL | 邮箱 |
| department_id | BIGINT | 否 | NULL | 部门ID |
| position | VARCHAR(50) | 否 | NULL | 职位 |
| status | TINYINT | 是 | 1 | 状态：1在职，0离职 |
| hire_date | DATE | 否 | NULL | 入职日期 |
| deleted | TINYINT | 是 | 0 | 是否删除：0否，1是 |
| create_time | DATETIME | 是 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | 是 | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

索引：

- PRIMARY KEY (id)
- UNIQUE KEY uk_employee_no (employee_no)
- KEY idx_department_id (department_id)
- KEY idx_name (name)
- KEY idx_status (status)

## department

部门表。

| 字段 | 类型 | 必填 | 默认值 | 说明 |
| --- | --- | --- | --- | --- |
| id | BIGINT | 是 | AUTO_INCREMENT | 部门ID，主键 |
| name | VARCHAR(50) | 是 | 无 | 部门名称，唯一 |
| parent_id | BIGINT | 否 | NULL | 父部门ID |
| sort_order | INT | 是 | 0 | 排序 |
| status | TINYINT | 是 | 1 | 状态：1启用，0禁用 |
| deleted | TINYINT | 是 | 0 | 是否删除：0否，1是 |
| create_time | DATETIME | 是 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | 是 | CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

索引：

- PRIMARY KEY (id)
- UNIQUE KEY uk_name (name)

## 约束

- 数据库使用 MySQL 8。
- 字符集使用 utf8mb4。
- 不使用数据库外键。
- 删除使用 deleted 字段做逻辑删除。
