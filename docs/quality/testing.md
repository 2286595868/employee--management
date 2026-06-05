# Testing Guide

## 一、目标

本文档约束项目测试类型、命名、运行命令、覆盖要求和手动验证方式。

后续 Sprint 完成后，code-agent 必须更新 `docs/progress.md`，记录实际执行过的验证。

## 二、后端测试命令

后端测试使用 Maven Wrapper：

```bash
cd backend
./mvnw test
```

规则：

1. 不要求本机预装 Maven。
2. 不允许删除测试来通过构建。
3. 不允许降低断言强度来掩盖问题。
4. 测试失败时必须修复代码或说明明确阻塞原因。

## 三、测试类型

Controller 测试：

1. 使用 `@WebMvcTest`。
2. 验证 HTTP 状态码。
3. 验证统一响应结构。
4. 验证参数校验失败时的错误码和提示。

Service 测试：

1. 使用 JUnit 5。
2. 覆盖业务规则。
3. 覆盖业务异常。
4. 涉及事务时验证成功和失败路径。

Mapper 测试：

1. 后续业务 Sprint 如需要真实 SQL 验证，应优先使用独立测试库。
2. 测试 SQL 字段必须符合 `docs/design/database.md`。
3. 查询逻辑删除表时必须验证 `deleted = 0`。

## 四、命名规范

测试类：

1. Controller 测试命名为 `XxxControllerTest`。
2. Service 测试命名为 `XxxServiceTest`。
3. Mapper 测试命名为 `XxxMapperTest`。
4. 全局异常处理测试命名为 `GlobalExceptionHandlerTest`。

测试方法：

1. 使用能描述行为的英文方法名。
2. 推荐格式为 `methodOrScenarioReturnsExpectedResult`。
3. 不使用无意义名称，例如 `test1`、`testOk`。

## 五、基础覆盖要求

每个 Sprint 至少覆盖本 Sprint 新增或变更的核心行为。

Sprint 1 基础要求：

1. 健康检查接口返回统一响应结构。
2. 参数校验异常返回 `code = 400`。
3. 业务异常返回对应错误码。
4. 系统异常返回 `code = 500`，且不返回内部异常细节。

后续员工 CRUD Sprint 基础要求：

1. 新增员工参数校验。
2. `employeeNo` 重复。
3. 员工不存在返回 404。
4. 逻辑删除。
5. 分页查询和筛选条件。

## 六、手动验证

后端启动：

```bash
cd backend
./mvnw spring-boot:run
```

健康检查：

```bash
curl http://localhost:8080/api/health
```

预期返回：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "status": "UP"
  }
}
```

## 七、数据库连接验证

如本机有 MySQL 8：

1. 创建或确认存在 `employee_management` 数据库。
2. 执行 `mysql -u root -p < scripts/init-database.sql`。
3. 使用环境变量启动后端：

```bash
cd backend
DB_URL=jdbc:mysql://localhost:3306/employee_management DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

4. 确认应用可以启动，日志中没有数据库连接配置错误。

如果本机没有 MySQL：

1. 必须执行 `./mvnw test`。
2. 必须保留健康检查验证。
3. 必须在 `docs/progress.md` 记录真实数据库连接验证未执行及原因。

## 八、前端测试

当前 Sprint 1 不修改前端。

后续涉及前端时至少执行：

```bash
cd frontend
npm run build
```

如新增交互页面，还需要手动打开 Vite 本地地址验证核心流程。

## 九、记录要求

每个 Sprint 完成后，`docs/progress.md` 必须记录：

1. 完成内容。
2. 执行过的自动测试。
3. 执行过的手动验证。
4. 未执行验证及原因。
5. 遗留问题。
