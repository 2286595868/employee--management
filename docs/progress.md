# Progress

## 当前状态

Sprint 4 前端员工新增、编辑、删除已实现，前端生产构建验证和真实前后端联调已完成，等待 review-agent 审核。

说明：

- Sprint 1 后端基础规范、数据库连接准备和 MySQL 8 真实连接验证已完成，状态保持不变。
- Sprint 2 只覆盖员工后端 CRUD 和部门列表接口，不包含前端、登录、权限或 Excel 导入导出。
- Sprint 3 只覆盖前端员工列表页面、姓名搜索、部门筛选、状态筛选、分页切换和基础错误提示，不包含新增、编辑、删除、登录、权限或 Excel 导入导出。
- Sprint 4 只覆盖前端员工新增、编辑、删除、表单校验、操作结果提示和列表刷新，不包含登录、权限、Excel、批量操作或后端接口修改。

## 已完成

- 创建基础目录结构
- 创建 AGENTS.md
- 创建 ARCHITECTURE.md
- 创建数据库设计文档
- 创建产品需求文档
- 创建接口文档
- 创建审核清单
- 创建 Sprint 0 初始化计划
- 创建 Spring Boot 后端基础项目
- 创建 Vue 3 前端基础项目
- 创建 GET /api/health 健康检查接口
- 创建 scripts/init-database.sql
- 更新 docs/generated/db-schema.md
- 补充 README.md 启动说明
- 创建 .gitignore
- 补充 backend Maven Wrapper
- 完成后端测试验证
- 完成后端启动验证
- 完成 GET /api/health 实际 HTTP 验证
- 完成前端依赖安装
- 完成前端生产构建验证
- Sprint 0 已通过 review-agent 复审
- 创建 Sprint 1 后端基础计划
- 补充 docs/design/backend.md 后端工程规范
- 补充 docs/quality/testing.md 测试规范
- 增加 Spring Boot Validation 依赖
- 完善 Spring Boot 数据库连接配置，支持 DB_URL、DB_USERNAME、DB_PASSWORD 环境变量覆盖
- 完善 MyBatis 基础配置，包含 mapper XML 扫描、Entity 类型别名包和下划线转驼峰
- 增加 MyBatis Mapper 扫描配置
- 完善统一响应结构，支持成功和失败响应
- 增加统一错误码定义
- 增加业务异常基础类型
- 增加全局异常处理，覆盖参数校验异常、业务异常和系统异常
- 增加参数校验分组基础结构
- 保留 GET /api/health 健康检查接口
- 补充健康检查和全局异常处理测试
- 完成 Sprint 1 后端测试验证
- 完成 Sprint 1 后端启动验证
- 完成 Sprint 1 GET /api/health 实际 HTTP 验证
- 完成 MySQL 8.4.8 Server 真实连接验证
- 创建 employee_management 数据库
- 执行 scripts/init-database.sql 初始化数据库
- 验证 employee 和 department 表创建成功
- 验证 employee 和 department 表关键字段及索引创建成功
- 创建 Sprint 2 员工后端 CRUD 计划
- 实现员工分页查询、新增、更新、逻辑删除
- 实现部门列表接口，供员工表单下拉使用
- 增加员工和部门 Entity、DTO、VO、Mapper、Service、Controller 及必要测试
- 完成 `cd backend && ./mvnw test`
- 完成真实 MySQL 8.4.8-arm64 环境验证
- 完成员工 CRUD 和部门列表接口 HTTP 验证
- 创建 Sprint 3 前端员工列表页面计划
- 实现前端员工列表页面
- 实现员工分页查询 API 封装
- 实现部门列表 API 封装
- 实现姓名搜索、部门筛选、状态筛选和重置筛选
- 实现分页页码切换和每页数量切换
- 实现员工表格字段展示、性别和状态枚举文本展示、空值占位展示
- 实现员工列表加载态和基础错误提示
- 完成 Sprint 3 前端生产构建验证
- 完成 Sprint 3 前后端真实联调验证
- 完成 Sprint 3 联调验证记录更新
- Sprint 3 已通过 review-agent 复审
- Sprint 3 计划文件已归档到 `docs/plans/completed/sprint-3-employee-list-page.md`
- 创建 Sprint 4 前端员工新增、编辑、删除计划
- 在员工管理页面增加新增员工入口
- 在员工列表操作列增加编辑和删除入口
- 实现新增员工弹窗和编辑员工弹窗
- 实现员工表单必填、枚举、长度、邮箱和日期格式校验
- 实现员工新增、更新、删除 API 封装
- 实现删除确认框，确认内容包含员工姓名和员工编号
- 实现新增、编辑、删除提交态和错误提示
- 实现新增成功后回到第 1 页刷新列表
- 实现编辑成功后保持当前页和筛选条件刷新列表
- 实现删除当前页最后一条数据时的页码回退处理
- 完成 Sprint 4 前端生产构建验证
- 完成 Sprint 4 真实前后端联调验证

## 未完成

- Sprint 4 等待 review-agent 审核

## 下一步

使用 review-agent 审核 Sprint 4 是否满足 Sprint Contract。

## 验证情况

当前已完成：

- java -version：通过，当前环境为 OpenJDK 17。
- node -v：通过，当前环境为 Node.js v23.11.0。
- npm -v：通过，当前环境为 npm 10.9.2。
- npm install：通过。
- npm run build：通过。
- backend Maven Wrapper：已补充。
- ./mvnw test：通过，4 个测试通过。
- ./mvnw spring-boot:run：通过，后端成功启动在 8080。
- curl http://localhost:8080/api/health：通过，返回 {"code":0,"message":"success","data":{"status":"UP"}}。
- mysql --version：可执行，但当前客户端为 MySQL 5.7.24，不是项目要求的 MySQL 8。
- /usr/local/mysql/bin/mysql --version：通过，当前 MySQL 客户端为 8.4.8 arm64。
- /usr/local/mysql/bin/mysql -h127.0.0.1 -P3306 -uroot：通过，已连接 MySQL Server 8.4.8。
- scripts/init-database.sql：通过，已成功执行。
- SHOW DATABASES LIKE 'employee_management'：通过，employee_management 数据库存在。
- SHOW TABLES FROM employee_management：通过，department 和 employee 表存在。
- SHOW COLUMNS FROM employee：通过，字段符合 scripts/init-database.sql。
- SHOW COLUMNS FROM department：通过，字段符合 scripts/init-database.sql。
- SHOW INDEX FROM employee 和 SHOW INDEX FROM department：通过，主键和唯一/普通索引已创建。
- 通过 `/private/tmp/mysqlcase.sparseimage` 创建 case-sensitive APFS 载体并初始化 MySQL 8.4.8 实例。
- `/usr/local/mysql/bin/mysqld --initialize-insecure --basedir=/usr/local/mysql --datadir=/Volumes/mysqlcase/data`：通过。
- `/usr/local/mysql/bin/mysqld --basedir=/usr/local/mysql --datadir=/Volumes/mysqlcase/data --port=3307 --socket=/private/tmp/mysqlcase.sock --daemonize`：通过。
- `/usr/local/mysql/bin/mysql --protocol=TCP -h127.0.0.1 -P3307 -uroot -e \"SELECT VERSION() AS version;\"`：通过，返回 8.4.8。
- `/usr/local/mysql/bin/mysql --protocol=TCP -h127.0.0.1 -P3307 -uroot -e \"SOURCE /Users/jiamingliang/Desktop/employee-management/scripts/init-database.sql;\"`：通过，成功初始化 `employee_management`。
- `SHOW TABLES / SHOW COLUMNS / SHOW INDEX`：通过，employee 和 department 表字段及索引符合设计文档。
- `GET /api/health`：通过，返回统一响应。
- `GET /api/departments`：通过，返回启用且未删除部门，并按 `sort_order` 升序。
- `POST /api/employees`：通过，成功新增员工。
- 重复 `employeeNo` 新增：通过，返回 400 和“员工编号已存在”。
- `GET /api/employees?page=1&pageSize=10`：通过，返回分页数据和 `departmentName`。
- `PUT /api/employees/1`：通过，成功更新员工。
- `PUT /api/employees/999`：通过，返回 404 和“员工不存在”。
- `DELETE /api/employees/1`：通过，执行逻辑删除。
- 删除后再次分页查询：通过，结果为空。
- 删除已删除员工：通过，返回 404 和“员工不存在”。
- MySQL 直查验证：通过，`employee.deleted = 1`。
- Sprint 3 本次真实联调环境：
  - MySQL：`/usr/local/mysql/bin/mysqld`，版本 8.4.8。
  - MySQL 地址和端口：`127.0.0.1:3318`。
  - MySQL socket：`/private/tmp/employee-management-sprint3.sock`。
  - MySQL 临时数据目录：`/private/tmp/employee-management-sprint3-mysql2`。
  - 数据库名：`employee_management`。
  - 数据库初始化命令：`/usr/local/mysql/bin/mysql --protocol=TCP -h127.0.0.1 -P3318 -uroot -e "SOURCE /Users/jiamingliang/Desktop/employee-management/scripts/init-database.sql;"`。
  - 验证数据：3 个部门，13 个员工；员工编号使用 `S3-E001` 到 `S3-E013`。
  - 后端启动命令：`cd backend && DB_URL='jdbc:mysql://127.0.0.1:3318/employee_management?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai' DB_USERNAME=root DB_PASSWORD= ./mvnw spring-boot:run`。
  - 后端端口：8080。
  - 前端启动命令：`cd frontend && npm run dev -- --host 127.0.0.1`。
  - 前端实际端口：5174；启动时 5173 已被占用，Vite 自动切换到 `http://127.0.0.1:5174/`。
  - Vite proxy 配置：`frontend/vite.config.js` 中 `server.proxy['/api'].target = 'http://localhost:8080'`，`changeOrigin = true`。
  - 页面级验证工具：Google Chrome 148 临时调试实例，启动参数包含 `--remote-debugging-port=9222 --user-data-dir=/private/tmp/chrome-sprint3`；通过 Chrome DevTools Protocol 操作真实页面。
- `cd frontend && npm run build`：通过，Vite 构建成功；构建过程中出现 Rollup 对依赖注释的提示和 chunk size 警告，不影响构建结果。
- `curl http://localhost:8080/api/health`：通过，返回 `{"code":0,"message":"success","data":{"status":"UP"}}`。
- `curl http://127.0.0.1:5174/`：通过，返回前端 HTML 入口。
- `curl http://127.0.0.1:5174/api/employees?page=1&pageSize=10`：通过，返回员工分页数据，`total=13`。
- `curl http://127.0.0.1:5174/api/departments`：通过，返回部门列表数据，包含技术部、人事部、财务部。
- 页面首次加载员工列表：通过。Chrome DevTools Protocol 观察到页面首次加载请求 `GET /api/departments` 和 `GET /api/employees?page=1&pageSize=10`；页面展示 `共 13 条`，第一页展示 `S3-E013` 到 `S3-E004`。
- 页面姓名搜索：通过。在姓名输入框输入 `张三` 并点击搜索；观察到请求 `GET /api/employees?page=1&pageSize=10&name=张三`；页面展示 `共 1 条`，记录为 `S3-E001 / 张三`。
- 页面部门筛选：通过。选择部门 `技术部` 并点击搜索；观察到请求 `GET /api/employees?page=1&pageSize=10&departmentId=1`；页面展示 `共 5 条`，列表部门均为技术部。
- 页面状态筛选：通过。选择状态 `离职` 并点击搜索；观察到请求 `GET /api/employees?page=1&pageSize=10&status=0`；页面展示 `共 5 条`，列表状态均为离职。
- 页面分页切换：通过。在 13 条数据、每页 10 条状态下点击页码 2；观察到请求 `GET /api/employees?page=2&pageSize=10`；页面展示 `S3-E003`、`S3-E002`、`S3-E001`，且不再展示第一页的 `S3-E013`。
- 页面每页数量切换：通过。在分页组件中选择 `20/page`；观察到请求 `GET /api/employees?page=1&pageSize=20`；页面展示 `共 13 条`，同页可见 `S3-E013` 和 `S3-E001`。
- 页面重置筛选：通过。先输入 `张三` 并搜索，再点击重置；观察到请求 `GET /api/employees?page=1&pageSize=20`，请求中不再包含 `name`、`departmentId` 或 `status`；页面筛选项恢复为 `全部部门`、`全部状态`，列表恢复为 `共 13 条`。
- 页面接口失败提示：通过。验证方法不是 curl 后端错误响应替代，而是在真实页面中触发：
  1. 保持前端 `http://127.0.0.1:5174/` 页面打开。
  2. 停止后端 8080 进程。
  3. 在页面点击搜索按钮。
  4. Vite proxy 请求 `GET /api/employees?page=1&pageSize=20` 返回 HTTP 500，底层原因是后端连接拒绝。
  5. 页面出现 Element Plus 错误提示，提示内容为 `Request failed with status code 500`。
- `curl http://127.0.0.1:5174/api/employees?page=1&pageSize=10&name=图灵`：通过，返回空列表结果；页面空数据状态由 `el-table` 的 `暂无员工数据` 承载。
- Sprint 4 本次真实联调环境：
  - 日期：2026-06-08。
  - MySQL：`/usr/local/mysql/bin/mysqld`，版本 8.4.8。
  - MySQL 地址和端口：`127.0.0.1:3320`。
  - MySQL socket：`/private/tmp/employee-management-sprint4.sock`。
  - MySQL 临时数据目录：`/Volumes/mysqlcase/sprint4-data`。
  - 数据库名：`employee_management`。
  - 数据库初始化命令：`/usr/local/mysql/bin/mysql --protocol=TCP -h127.0.0.1 -P3320 -uroot -e "SOURCE /Users/jiamingliang/Desktop/employee-management/scripts/init-database.sql;"`。
  - 验证数据：3 个部门和 3 个基础员工，员工编号为 `S4-E001`、`S4-E002`、`S4-E003`。
  - 后端启动命令：`cd backend && DB_URL='jdbc:mysql://127.0.0.1:3320/employee_management?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai' DB_USERNAME=root DB_PASSWORD= ./mvnw spring-boot:run`。
  - 后端端口：8080。
  - 前端启动命令：`cd frontend && npm run dev -- --host 127.0.0.1`。
  - 前端实际端口：5174；5173 已被其他 Node 进程占用，Vite 自动切换端口。
  - 页面级验证工具：Google Chrome headless 临时调试实例，调试端口 9224，用户数据目录 `/private/tmp/chrome-sprint4`。
- `cd frontend && npm run build`：通过，Vite 生产构建成功；存在 Rollup 依赖注释提示和 chunk size 警告，不影响构建结果。
- `GET /api/health`：通过，后端返回 `{"code":0,"message":"success","data":{"status":"UP"}}`。
- 页面首次加载员工列表：通过，页面展示 Sprint 4 基础员工数据。
- 新增空表单校验：通过，员工编号和姓名为空时展示校验提示，未调用 `POST /api/employees`。
- 新增员工成功：通过，页面调用 `POST /api/employees`，弹窗关闭，展示成功提示，回到第 1 页并刷新列表。
- 新增员工失败：通过，使用重复员工编号时展示“员工编号已存在”，弹窗保持打开。
- 编辑员工回填：通过，点击编辑后弹窗正确回填当前行员工数据。
- 编辑员工成功：通过，页面调用 `PUT /api/employees/{id}`，弹窗关闭，展示成功提示并刷新当前列表。
- 编辑员工失败：通过，编辑为重复员工编号时展示“员工编号已存在”，弹窗保持打开。
- 删除取消：通过，确认框包含员工姓名和员工编号，点击取消后未调用删除接口。
- 删除员工成功：通过，页面调用 `DELETE /api/employees/{id}`，展示成功提示并刷新列表。
- 删除员工失败：通过，在确认框打开后由后端提前删除同一员工，再从页面确认删除，页面收到 404 并展示“员工不存在”。
- 筛选条件保留：通过，在状态为离职的筛选结果中编辑员工，编辑成功刷新后仍保持离职筛选条件。
- 最终页面视觉检查：通过，新增按钮、操作列、表格和分页没有明显重叠。

当前未完成：

- 无 Sprint 1 数据库连接验证遗留问题。
- 无 Sprint 2 真实 MySQL 验证遗留问题。
- 无 Sprint 3 功能性遗留问题。
- 无 Sprint 4 功能性遗留问题，等待 review-agent 审核。

后续验证方式：

后端：

1. cd backend
2. ./mvnw test
3. ./mvnw spring-boot:run
4. curl http://localhost:8080/api/health

前端：

1. cd frontend
2. npm install
3. npm run dev
4. 打开 Vite 输出的本地地址

数据库：

1. mysql -u root -p < scripts/init-database.sql
2. 对照 docs/design/database.md 和 docs/generated/db-schema.md
3. 或按本次 Sprint 3 记录的 3318 真实 MySQL 验证命令执行

本次 Sprint 1 未实现员工 CRUD、部门业务接口、前端员工页面、登录和权限。
本次 Sprint 2 已实现员工后端 CRUD 和部门列表接口，未涉及前端、登录、权限和 Excel 导入导出。
本次 Sprint 3 已实现前端员工列表、搜索和分页，未涉及新增、编辑、删除、登录、权限和 Excel 导入导出。
本次 Sprint 4 已实现前端员工新增、编辑、删除，未涉及登录、权限、Excel、批量操作、后端接口或数据库结构修改。

说明：

- 系统默认 `mysql` 命令仍指向 MySQL 5.7.24 客户端。
- 本次真实验证使用 `/usr/local/mysql/bin/mysql`，对应已安装的 MySQL 8.4.8 Server。
- 由于系统 3306 服务不可直接使用，本次 Sprint 3 验收记录修复在 `/private/tmp/employee-management-sprint3-mysql2` 上启动了独立的 MySQL 8.4.8 临时实例，端口为 3318，socket 为 `/private/tmp/employee-management-sprint3.sock`。
- Sprint 4 验证使用 case-sensitive APFS 载体 `/Volumes/mysqlcase` 下的独立临时数据目录，MySQL 端口为 3320。
- 本次未在文档或配置文件中记录本机 MySQL 密码。
