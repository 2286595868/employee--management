# Sprint 0 Init

## 一、本次目标

Sprint 0 的目标是初始化员工管理系统的基础工程结构。

本次只做项目初始化，不实现具体业务功能。

需要完成：

1. 初始化 Spring Boot 后端项目
2. 初始化 Vue 3 前端项目
3. 创建数据库初始化 SQL 脚本
4. 补充 README.md 启动说明
5. 确认后端和前端可以启动
6. 更新 docs/progress.md

## 二、本次不做什么

Sprint 0 不做以下内容：

1. 不实现员工新增
2. 不实现员工查询
3. 不实现员工编辑
4. 不实现员工删除
5. 不实现部门管理接口
6. 不实现登录
7. 不实现权限管理
8. 不实现复杂前端页面
9. 不引入 JWT
10. 不做 Excel 导入导出

本次只允许创建基础项目、基础配置、数据库初始化脚本和启动说明。

## 三、技术栈约定

后端：

- Java 17
- Spring Boot 3
- Maven
- MyBatis
- MySQL Driver

前端：

- Vue 3
- Vite
- Axios
- Element Plus

数据库：

- MySQL 8

## 四、预计新增或修改文件

后端相关：

- backend/pom.xml
- backend/src/main/java/com/example/employee/EmployeeApplication.java
- backend/src/main/resources/application.yml
- backend/src/main/resources/mapper/
- backend/src/test/

前端相关：

- frontend/package.json
- frontend/index.html
- frontend/src/main.js 或 frontend/src/main.ts
- frontend/src/App.vue
- frontend/src/api/
- frontend/src/views/
- frontend/src/components/

数据库相关：

- scripts/init-database.sql
- docs/generated/db-schema.md

文档相关：

- README.md
- docs/progress.md

## 五、Sprint Contract

Sprint 0 完成标准如下：

1. backend 目录中存在可启动的 Spring Boot 项目。
2. frontend 目录中存在可启动的 Vue 3 项目。
3. scripts/init-database.sql 中包含 employee 和 department 表的建表 SQL。
4. docs/generated/db-schema.md 中记录当前数据库表结构快照。
5. README.md 中包含项目说明、技术栈、目录结构、后端启动方式、前端启动方式、数据库初始化方式。
6. 后端至少提供一个健康检查接口，例如 GET /api/health。
7. 后端启动后，访问 /api/health 可以返回统一响应格式。
8. 前端启动后，可以看到基础首页。
9. 不允许实现员工 CRUD 业务功能。
10. 不允许实现登录和权限功能。
11. docs/progress.md 必须记录 Sprint 0 的完成情况、验证方式和遗留问题。

## 六、验证方式

后端验证：

1. 进入 backend 目录
2. 执行 mvn spring-boot:run
3. 访问 http://localhost:8080/api/health
4. 确认返回统一响应格式

前端验证：

1. 进入 frontend 目录
2. 执行 npm install
3. 执行 npm run dev
4. 打开 Vite 提示的本地地址
5. 确认页面可以正常显示

数据库验证：

1. 确认 scripts/init-database.sql 存在
2. 确认 SQL 中包含 employee 表
3. 确认 SQL 中包含 department 表
4. 确认表结构和 docs/design/database.md 一致

## 七、风险点

1. 本机可能没有安装 Maven。
2. 本机可能没有安装 Node.js。
3. 本机可能没有安装 MySQL。
4. 如果暂时没有 MySQL，本 Sprint 允许只创建 SQL 脚本，不强制真实执行数据库。
5. code-agent 容易扩大范围，所以必须明确禁止实现员工 CRUD。
6. README.md 必须写清楚启动命令，否则后续 agent 难以接手。

## 八、给 Code Subagent 的任务说明

请根据本文件执行 Sprint 0。

要求：

1. 先阅读 AGENTS.md、ARCHITECTURE.md、docs/design/database.md、docs/specs/product.md、docs/specs/api.md、docs/progress.md。
2. 创建 Spring Boot 后端基础项目。
3. 创建 Vue 3 前端基础项目。
4. 创建 scripts/init-database.sql。
5. 根据 scripts/init-database.sql 更新 docs/generated/db-schema.md。
6. 补充 README.md。
7. 更新 docs/progress.md。
8. 只做项目初始化，不实现员工 CRUD、登录、权限。
9. 完成后输出修改文件、验证方式、遗留问题。
