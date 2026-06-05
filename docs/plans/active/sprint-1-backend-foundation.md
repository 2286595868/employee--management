# Sprint 1 Backend Foundation

## 一、本次目标

Sprint 1 的目标是补齐后端基础规范和数据库连接准备，为后续员工 CRUD、部门接口等业务开发提供稳定基础。

本 Sprint 只做后端基础能力，不实现员工、部门、登录、权限和前端业务页面。

需要完成：

1. 补充 `docs/design/backend.md` 后端工程规范。
2. 补充 `docs/quality/testing.md` 测试规范。
3. 完善 Spring Boot 数据库连接配置。
4. 完善 MyBatis 基础配置。
5. 完善统一响应结构。
6. 增加全局异常处理。
7. 增加参数校验基础能力。
8. 保留并验证健康检查接口 `GET /api/health`。

## 二、本次不做什么

Sprint 1 明确不做以下内容：

1. 不实现员工 CRUD。
2. 不实现部门业务接口。
3. 不实现前端员工页面。
4. 不实现登录。
5. 不实现权限。
6. 不新增数据库表。
7. 不修改 employee 或 department 表结构。
8. 不引入 JWT、Session、OAuth 等认证方案。
9. 不实现 Excel 导入导出。
10. 不写死数据库密码、JWT 密钥或其他敏感信息。

## 三、Sprint Contract

Sprint 1 完成标准如下：

1. `docs/design/backend.md` 必须说明后端包结构、分层规则、DTO/VO/Entity 使用规则、配置管理规则、异常处理规则、参数校验规则、MyBatis 使用规则和日志安全规则。
2. `docs/quality/testing.md` 必须说明后端测试类型、命名规范、运行命令、基础覆盖要求、数据库连接验证方式、手动验证方式和禁止通过删除测试来通过构建。
3. 后端必须继续遵守 `Controller -> Service -> Mapper -> Database` 分层规则。
4. Controller 不允许直接调用 Mapper，不允许直接操作数据库，不允许直接返回 Entity。
5. Spring Boot 数据库连接配置必须支持通过环境变量覆盖 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD`。
6. 数据库密码不得硬编码到代码或文档示例的默认值中。
7. MyBatis 必须配置 mapper XML 扫描路径和下划线转驼峰映射。
8. 如果新增 Mapper 扫描配置，必须与 `com.example.employee.mapper` 包结构一致。
9. 统一响应结构必须保持为 `code`、`message`、`data`。
10. 成功响应必须继续使用 `code = 0` 和 `message = success`。
11. 失败响应必须按 `docs/specs/api.md` 使用明确错误码：参数错误 400、数据不存在 404、系统错误 500。
12. 全局异常处理必须至少覆盖参数校验异常、业务异常和未预期系统异常。
13. 系统异常响应不得把堆栈、SQL、数据库密码、token、cookie 等敏感信息返回给前端。
14. 参数校验基础能力必须引入并可用于后续 DTO 校验，例如 Jakarta Validation。
15. 健康检查接口 `GET /api/health` 必须保留。
16. 健康检查接口必须继续返回统一响应格式。
17. 本 Sprint 不允许新增 `/api/employees` 或 `/api/departments` 业务接口实现。
18. 本 Sprint 不允许新增前端员工管理页面。
19. 后端测试必须通过 `cd backend && ./mvnw test`。
20. `docs/progress.md` 必须由 code-agent 更新 Sprint 1 的完成情况、验证方式和遗留问题。

## 四、预计修改文件

文档相关：

- `docs/design/backend.md`
- `docs/quality/testing.md`
- `docs/progress.md`

后端相关：

- `backend/pom.xml`
- `backend/src/main/resources/application.yml`
- `backend/src/main/java/com/example/employee/common/ApiResponse.java`
- `backend/src/main/java/com/example/employee/exception/BusinessException.java`
- `backend/src/main/java/com/example/employee/exception/GlobalExceptionHandler.java`
- `backend/src/main/java/com/example/employee/common/ErrorCode.java`
- `backend/src/main/java/com/example/employee/config/MyBatisConfig.java`
- `backend/src/main/java/com/example/employee/controller/HealthController.java`
- `backend/src/main/java/com/example/employee/service/HealthService.java`
- `backend/src/main/java/com/example/employee/service/impl/HealthServiceImpl.java`
- `backend/src/test/java/com/example/employee/controller/HealthControllerTest.java`
- `backend/src/test/java/com/example/employee/exception/GlobalExceptionHandlerTest.java`

说明：

- 以上后端文件是预计范围，code-agent 可以按现有代码实际情况少建或调整文件名。
- 如果 `application.yml` 已满足部分配置要求，只需补齐缺失项并保持配置可读。
- 不应修改 `scripts/init-database.sql`，除非发现其与 `docs/design/database.md` 明显不一致；如需修改，必须先说明原因并同步数据库设计文档和快照。

## 五、风险点

1. 当前 `docs/design/backend.md` 和 `docs/quality/testing.md` 可能为空，规范需要一次性写清楚，避免后续 Sprint 标准不一致。
2. Spring Boot 3 默认不一定包含参数校验依赖，可能需要补充 `spring-boot-starter-validation`。
3. 引入数据库连接配置后，本地没有 MySQL 时可能影响启动或测试，需要区分普通单元测试和真实数据库连接验证。
4. MyBatis 配置容易提前引入业务 Mapper 示例，必须避免变相实现员工或部门接口。
5. 全局异常处理如果捕获过宽，可能掩盖测试中的真实 bug；测试应覆盖关键异常映射。
6. 统一响应结构调整可能影响现有健康检查测试，需要同步更新测试断言。
7. 日志处理必须避免打印敏感配置，尤其是数据库密码、token、cookie。
8. code-agent 可能扩大范围实现 CRUD，必须以本 Sprint Contract 的禁止项为准。

## 六、验证方式

自动验证：

1. `cd backend`
2. `./mvnw test`

启动验证：

1. `cd backend`
2. `./mvnw spring-boot:run`
3. `curl http://localhost:8080/api/health`
4. 确认返回：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "status": "UP"
  }
}
```

数据库连接验证：

1. 确认本机 MySQL 8 已存在 `employee_management` 数据库。
2. 如未初始化，先执行 `mysql -u root -p < scripts/init-database.sql`。
3. 使用环境变量启动后端：

```bash
DB_URL=jdbc:mysql://localhost:3306/employee_management DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

4. 确认应用启动日志无数据库连接配置错误。

说明：

- 如果当前环境没有可用 MySQL，code-agent 必须在 `docs/progress.md` 中记录真实数据库连接验证未执行，并保留 `./mvnw test` 与健康检查验证结果。

## 七、给 Code Agent 的任务清单

请根据本文件执行 Sprint 1。

执行前必须先阅读：

1. `README.md`
2. `ARCHITECTURE.md`
3. `docs/specs/product.md`
4. `docs/specs/api.md`
5. `docs/design/database.md`
6. `docs/generated/db-schema.md`
7. `docs/progress.md`
8. `docs/quality/review-checklist.md`
9. `docs/plans/active/sprint-1-backend-foundation.md`

具体任务：

1. 补充 `docs/design/backend.md`，写清后端工程规范。
2. 补充 `docs/quality/testing.md`，写清测试规范和验证方式。
3. 检查并完善 `backend/pom.xml`，确保包含 Web、MyBatis、MySQL、Validation、Test 所需依赖。
4. 检查并完善 `backend/src/main/resources/application.yml`，确保数据库连接和 MyBatis 基础配置满足 Contract。
5. 完善 `ApiResponse`，提供成功响应和失败响应的统一创建方式。
6. 新增或完善错误码定义，保持与 `docs/specs/api.md` 一致。
7. 新增业务异常类型，供后续 Service 层抛出明确业务错误。
8. 新增全局异常处理，覆盖参数校验异常、业务异常和系统异常。
9. 为参数校验准备基础能力，但不要创建员工或部门 DTO。
10. 保留健康检查接口，并确认其仍通过 Service 层返回统一响应。
11. 补充必要测试，至少覆盖健康检查响应格式和全局异常响应映射。
12. 执行 `cd backend && ./mvnw test`。
13. 如环境允许，执行后端启动和 `GET /api/health` 手动验证。
14. 如环境允许，使用真实 MySQL 执行数据库连接验证；如不允许，记录未执行原因。
15. 更新 `docs/progress.md`，记录 Sprint 1 完成内容、验证结果和遗留问题。

禁止事项：

1. 不得实现员工 CRUD。
2. 不得实现部门业务接口。
3. 不得实现前端员工页面。
4. 不得实现登录和权限。
5. 不得删除测试来通过构建。
6. 不得绕过 Controller、Service、Mapper、Database 分层。
7. 不得把 Entity 直接返回给前端。
8. 不得写死数据库密码或其他敏感信息。

## 八、给 Review Agent 的验收重点

review-agent 审核 Sprint 1 时重点检查：

1. 是否严格满足本 Sprint Contract。
2. 是否没有实现员工 CRUD、部门业务接口、前端员工页面、登录和权限。
3. `docs/design/backend.md` 和 `docs/quality/testing.md` 是否足够指导后续 Sprint。
4. 数据库配置是否可通过环境变量覆盖，且无硬编码密码。
5. MyBatis 基础配置是否与项目包结构一致。
6. 统一响应结构是否与 `docs/specs/api.md` 一致。
7. 全局异常处理是否避免泄露堆栈和敏感信息。
8. 参数校验基础能力是否可用于后续 DTO。
9. 健康检查接口是否保留并返回统一响应。
10. 测试和 `docs/progress.md` 是否同步更新。
