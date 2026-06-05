# Sprint 2 Employee Backend CRUD

## 一、本次目标

Sprint 2 的目标是实现员工管理后端 CRUD 能力，并提供员工表单可使用的部门下拉数据接口。

本 Sprint 只实现后端业务接口，不实现前端员工页面，不实现登录、权限和 Excel 导入导出。

需要完成：

1. 分页查询员工。
2. 新增员工。
3. 更新员工。
4. 逻辑删除员工。
5. 查询部门列表接口，供员工新增和编辑表单下拉选择使用。
6. 补充必要的 Controller、Service、Mapper 测试或真实数据库验证方式。
7. 基于真实 MySQL 8.4.8-arm64 环境完成验证并记录结果。

## 二、本次不做什么

Sprint 2 明确不做以下内容：

1. 不实现前端员工页面。
2. 不实现前端 API 封装、员工列表页、新增弹窗、编辑弹窗或删除确认弹窗。
3. 不实现登录。
4. 不实现权限。
5. 不实现 JWT、Session、OAuth 或角色控制。
6. 不实现 Excel 导入。
7. 不实现 Excel 导出。
8. 不新增数据库表。
9. 不修改 employee 或 department 表结构，除非先更新 `docs/design/database.md` 并说明原因。
10. 不实现部门新增、更新、删除等部门管理功能。
11. 不引入未在当前技术栈中约定的持久层框架。

## 三、Sprint Contract

Sprint 2 完成标准如下：

1. 必须实现 `GET /api/employees` 分页查询员工接口。
2. 分页查询必须支持 `page`、`pageSize`、`name`、`departmentId`、`status` 查询参数。
3. `page` 默认值为 1，`pageSize` 默认值为 10。
4. 分页查询必须只返回 `employee.deleted = 0` 的员工。
5. `name` 查询必须按员工姓名模糊匹配。
6. `departmentId` 查询必须按员工所属部门过滤。
7. `status` 查询必须只接受 0 或 1。
8. 分页查询返回数据必须符合 `docs/specs/api.md`，包含 `total` 和 `records`。
9. 员工列表记录必须返回 VO，不允许直接返回 Entity。
10. 员工列表记录必须包含 `departmentName`；员工无部门时允许为空。
11. 必须实现 `POST /api/employees` 新增员工接口。
12. 新增员工请求体必须使用 DTO 接收。
13. 新增员工必须校验 `employeeNo` 不能为空且不能重复。
14. 新增员工必须校验 `name` 不能为空。
15. 新增员工必须校验 `gender` 只能是 0、1、2。
16. 新增员工必须校验 `status` 只能是 0、1。
17. 新增员工中 `departmentId` 不为空时，必须校验部门存在且可用。
18. 新增员工不得允许创建到 `deleted = 1` 的部门下。
19. 新增员工成功响应必须使用统一响应格式。
20. 必须实现 `PUT /api/employees/{id}` 更新员工接口。
21. 更新员工请求体必须使用 DTO 接收。
22. 更新员工必须校验路径 `id` 存在。
23. 更新员工不得更新不存在的员工。
24. 更新员工不得更新 `deleted = 1` 的员工。
25. 更新员工时 `employeeNo` 不允许与其他未删除员工重复。
26. 更新员工中 `departmentId` 不为空时，必须校验部门存在且可用。
27. 更新员工必须遵守 `docs/specs/api.md` 的字段规则。
28. 必须实现 `DELETE /api/employees/{id}` 逻辑删除员工接口。
29. 删除员工必须只把 `deleted` 设置为 1，不允许物理删除。
30. 删除不存在或已删除员工时必须返回 404。
31. 必须实现 `GET /api/departments` 查询部门列表接口。
32. 部门列表必须只返回 `department.deleted = 0` 且 `department.status = 1` 的部门。
33. 部门列表必须按 `sort_order` 升序排序。
34. 部门列表返回必须使用 DepartmentVO，不允许直接返回 Entity。
35. 所有接口必须返回统一响应结构：`code`、`message`、`data`。
36. 成功响应必须使用 `code = 0` 和 `message = success`。
37. 参数错误必须按 `docs/specs/api.md` 返回 `code = 400`。
38. 数据不存在必须按 `docs/specs/api.md` 返回 `code = 404`。
39. 未预期系统错误必须按 `docs/specs/api.md` 返回 `code = 500`，且不得返回堆栈、SQL、数据库连接信息或敏感配置。
40. 后端必须遵守 `Controller -> Service -> Mapper -> Database` 分层。
41. Controller 只能接收请求、触发参数校验、调用 Service、返回统一响应。
42. Controller 不允许直接调用 Mapper。
43. Controller 不允许直接操作数据库。
44. Controller 不允许直接返回 Entity。
45. Service 负责业务校验、事务边界和调用 Mapper。
46. Mapper 只负责数据库访问，不允许写业务逻辑。
47. Entity 只允许在 Mapper 和 Service 内部使用，不允许作为接口响应返回给前端。
48. SQL 字段必须全部来自 `docs/design/database.md` 和 `docs/generated/db-schema.md`。
49. 查询业务数据必须正确过滤逻辑删除字段。
50. 用户输入必须通过 MyBatis 参数绑定传入，避免 SQL 注入。
51. 写操作涉及多次数据库检查和更新时，事务必须在 Service 层控制。
52. 不允许在代码、配置或文档中写死真实数据库密码。
53. 必须补充或更新必要测试，覆盖本 Sprint 新增核心行为。
54. 必须执行 `cd backend && ./mvnw test` 并记录结果。
55. 必须在真实 MySQL 8.4.8-arm64 环境验证员工 CRUD 和部门列表接口。
56. 真实 MySQL 验证必须使用 `employee_management` 数据库和 `scripts/init-database.sql` 初始化后的表结构。
57. 真实 MySQL 验证必须至少覆盖：分页查询、新增员工、重复员工编号、更新员工、逻辑删除员工、删除后不可查询、部门列表。
58. `docs/progress.md` 必须由 code-agent 更新 Sprint 2 的完成内容、测试结果、真实 MySQL 验证命令、验证结果和遗留问题。

## 四、接口范围

本 Sprint 只允许实现以下业务接口：

1. `GET /api/employees`
2. `POST /api/employees`
3. `PUT /api/employees/{id}`
4. `DELETE /api/employees/{id}`
5. `GET /api/departments`

除健康检查接口外，不允许新增其他业务接口。

## 五、预计修改文件

文档相关：

- `docs/progress.md`

后端 Controller：

- `backend/src/main/java/com/example/employee/controller/EmployeeController.java`
- `backend/src/main/java/com/example/employee/controller/DepartmentController.java`

后端 Service：

- `backend/src/main/java/com/example/employee/service/EmployeeService.java`
- `backend/src/main/java/com/example/employee/service/DepartmentService.java`
- `backend/src/main/java/com/example/employee/service/impl/EmployeeServiceImpl.java`
- `backend/src/main/java/com/example/employee/service/impl/DepartmentServiceImpl.java`

后端 Mapper：

- `backend/src/main/java/com/example/employee/mapper/EmployeeMapper.java`
- `backend/src/main/java/com/example/employee/mapper/DepartmentMapper.java`
- `backend/src/main/resources/mapper/EmployeeMapper.xml`
- `backend/src/main/resources/mapper/DepartmentMapper.xml`

后端 Entity：

- `backend/src/main/java/com/example/employee/entity/Employee.java`
- `backend/src/main/java/com/example/employee/entity/Department.java`

后端 DTO：

- `backend/src/main/java/com/example/employee/dto/EmployeeQueryDTO.java`
- `backend/src/main/java/com/example/employee/dto/EmployeeCreateDTO.java`
- `backend/src/main/java/com/example/employee/dto/EmployeeUpdateDTO.java`

后端 VO：

- `backend/src/main/java/com/example/employee/vo/PageResultVO.java`
- `backend/src/main/java/com/example/employee/vo/EmployeeVO.java`
- `backend/src/main/java/com/example/employee/vo/DepartmentVO.java`

后端测试：

- `backend/src/test/java/com/example/employee/controller/EmployeeControllerTest.java`
- `backend/src/test/java/com/example/employee/controller/DepartmentControllerTest.java`
- `backend/src/test/java/com/example/employee/service/EmployeeServiceTest.java`
- `backend/src/test/java/com/example/employee/service/DepartmentServiceTest.java`
- `backend/src/test/java/com/example/employee/mapper/EmployeeMapperTest.java`
- `backend/src/test/java/com/example/employee/mapper/DepartmentMapperTest.java`

说明：

- 以上是预计范围，code-agent 可以按现有代码实际情况少建或调整文件名。
- 如果已有通用分页 VO 或测试工具类，应优先复用。
- 不应修改 `scripts/init-database.sql`、`docs/design/database.md`、`docs/generated/db-schema.md`，除非发现三者与真实 MySQL 表结构明显不一致；如需修改，必须先说明原因并保持同步。
- 本 Sprint 不应修改 `frontend/` 下任何文件。

## 六、风险点

1. `employee.employee_no` 当前是全表唯一索引，逻辑删除后相同员工编号仍无法复用；code-agent 不能绕过数据库唯一约束，接口行为应明确返回参数错误或业务错误。
2. 部门表没有数据库外键，`departmentId` 是否存在必须由 Service 层校验。
3. 员工查询需要返回 `departmentName`，Mapper SQL 需要关联 department 表，同时不能误过滤掉无部门员工。
4. 查询员工时必须过滤 `employee.deleted = 0`，部门列表必须过滤 `department.deleted = 0` 和 `department.status = 1`。
5. 更新员工编号时容易误判自己与自己重复，重复校验必须排除当前员工 ID。
6. 删除必须是逻辑删除，不能使用 `DELETE FROM employee`。
7. Spring Boot 参数校验只能覆盖基础格式，部门存在、员工编号重复、员工存在等业务校验必须放在 Service。
8. MyBatis 动态 SQL 容易引入字符串拼接风险，必须使用参数绑定。
9. 真实 MySQL 8.4.8-arm64 验证依赖本机数据库状态，code-agent 需要记录实际使用的客户端路径、服务版本和验证 SQL/API 命令。
10. 测试数据可能污染本地数据库，真实验证应使用可识别的测试员工编号，并在验证后说明数据状态；如需要清理，只能使用逻辑删除或重新初始化测试库。
11. 如果 Sprint 1 仍待 review-agent 审核，code-agent 开始 Sprint 2 前应确认 Sprint 1 基础设施可用，避免在未稳定基础上叠加业务实现。
12. 本 Sprint 不做登录权限，接口不应增加认证拦截或鉴权逻辑。

## 七、验证方式

自动验证：

1. `cd backend`
2. `./mvnw test`

启动验证：

1. `cd backend`
2. 使用真实 MySQL 8.4.8-arm64 连接配置启动：

```bash
DB_URL=jdbc:mysql://localhost:3306/employee_management DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

3. 确认后端成功启动在 8080。
4. 确认 `GET /api/health` 仍返回统一响应格式。

真实 MySQL 8.4.8-arm64 验证：

1. 使用 `/usr/local/mysql/bin/mysql --version` 确认客户端为 MySQL 8.4.8 arm64。
2. 使用 `/usr/local/mysql/bin/mysql -h127.0.0.1 -P3306 -uroot -p` 连接本机 MySQL Server。
3. 确认服务端版本为 MySQL 8.4.8。
4. 如数据库未初始化，执行 `scripts/init-database.sql`。
5. 确认 `employee_management.employee` 和 `employee_management.department` 表结构与 `docs/generated/db-schema.md` 一致。
6. 启动后端后使用 HTTP 请求验证以下场景：
   - `GET /api/departments` 只返回启用且未删除部门，并按 `sort_order` 升序。
   - `GET /api/employees?page=1&pageSize=10` 返回分页结构。
   - `POST /api/employees` 可新增合法员工。
   - 重复 `employeeNo` 新增员工返回明确错误。
   - `PUT /api/employees/{id}` 可更新合法员工。
   - 更新不存在员工返回 404。
   - `DELETE /api/employees/{id}` 执行逻辑删除。
   - 删除不存在员工返回 404。
   - 删除后的员工不再出现在分页查询结果中。

说明：

- code-agent 必须在 `docs/progress.md` 记录真实执行过的命令和结果。
- 如果真实 MySQL 8.4.8-arm64 不可用，本 Sprint 不应标记完成；必须记录阻塞原因并交回处理。

## 八、给 Code Agent 的任务清单

请根据本文件执行 Sprint 2。

执行前必须先阅读：

1. `README.md`
2. `ARCHITECTURE.md`
3. `docs/specs/product.md`
4. `docs/specs/api.md`
5. `docs/design/database.md`
6. `docs/generated/db-schema.md`
7. `docs/design/backend.md`
8. `docs/quality/testing.md`
9. `docs/progress.md`
10. `docs/plans/active/sprint-2-employee-crud.md`

具体任务：

1. 确认 Sprint 1 后端基础设施可用，包括统一响应、错误码、业务异常、全局异常处理、参数校验和 MyBatis 配置。
2. 创建 Employee 和 Department Entity，字段必须严格来自数据库设计文档。
3. 创建 EmployeeQueryDTO、EmployeeCreateDTO、EmployeeUpdateDTO，并用 Jakarta Validation 覆盖基础参数规则。
4. 创建 EmployeeVO、DepartmentVO 和分页结果 VO，确保接口响应不暴露 Entity。
5. 创建 EmployeeMapper 和 DepartmentMapper，只负责数据库访问。
6. 创建 Mapper XML，使用 MyBatis 参数绑定和动态 SQL 实现分页、筛选、重复校验、按 ID 查询、插入、更新、逻辑删除、部门列表查询。
7. 创建 EmployeeService 和 DepartmentService 接口。
8. 创建 EmployeeServiceImpl 和 DepartmentServiceImpl，实现业务校验、DTO 到 Entity 转换、Entity 到 VO 转换和事务控制。
9. 创建 EmployeeController 和 DepartmentController，只负责 HTTP 入参、参数校验、调用 Service 和返回统一响应。
10. 实现 `GET /api/employees` 分页查询。
11. 实现 `POST /api/employees` 新增员工。
12. 实现 `PUT /api/employees/{id}` 更新员工。
13. 实现 `DELETE /api/employees/{id}` 逻辑删除员工。
14. 实现 `GET /api/departments` 部门列表查询。
15. 补充 Controller 测试，覆盖统一响应、参数校验和主要异常映射。
16. 补充 Service 测试，覆盖 employeeNo 重复、员工不存在、部门不存在、逻辑删除等业务规则。
17. 如项目测试环境允许，补充 Mapper 测试或等价真实 MySQL SQL 验证。
18. 执行 `cd backend && ./mvnw test`。
19. 使用真实 MySQL 8.4.8-arm64 启动后端并完成接口验证。
20. 更新 `docs/progress.md`，记录 Sprint 2 完成内容、自动测试、真实 MySQL 验证、未完成项和遗留风险。

禁止事项：

1. 不得实现前端员工页面。
2. 不得修改 `frontend/` 业务代码。
3. 不得实现登录和权限。
4. 不得实现 Excel 导入导出。
5. 不得删除测试来通过构建。
6. 不得绕过 Controller、Service、Mapper、Database 分层。
7. 不得让 Controller 直接调用 Mapper。
8. 不得把 Entity 直接返回给前端。
9. 不得物理删除员工数据。
10. 不得写死数据库密码或其他敏感信息。
11. 不得使用 `docs/design/database.md` 未定义的数据库字段。

## 九、给 Review Agent 的验收重点

review-agent 审核 Sprint 2 时重点检查：

1. 是否严格满足本 Sprint Contract。
2. 是否只实现员工后端 CRUD 和部门列表接口。
3. 是否没有实现前端员工页面、登录权限或 Excel 导入导出。
4. Controller 是否没有直接调用 Mapper。
5. Controller 是否没有直接返回 Entity。
6. Service 是否承担业务校验和事务边界。
7. Mapper 是否只做数据库访问，SQL 是否符合数据库设计文档。
8. 所有查询是否正确过滤逻辑删除字段。
9. 新增和更新员工是否正确校验 employeeNo 唯一性。
10. departmentId 不为空时是否校验部门存在且可用。
11. 删除员工是否只做逻辑删除。
12. API 响应结构和错误码是否符合 `docs/specs/api.md`。
13. 测试是否覆盖核心成功路径和失败路径。
14. `docs/progress.md` 是否记录真实 MySQL 8.4.8-arm64 验证过程和结果。
