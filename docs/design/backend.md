# Backend Design

## 一、目标

本文档约束后端工程结构、分层、配置、异常、校验、MyBatis 和日志安全规则。

后续 Sprint 写后端代码时，必须同时遵守：

1. `AGENTS.md`
2. `ARCHITECTURE.md`
3. `docs/specs/api.md`
4. `docs/design/database.md`
5. `docs/design/backend.md`

## 二、技术栈

- Java 17
- Spring Boot 3
- Maven Wrapper
- MyBatis
- MySQL 8
- Jakarta Validation

## 三、包结构

后端根包固定为：

```text
com.example.employee
```

推荐结构：

```text
com.example.employee
├── controller
├── service
│   └── impl
├── mapper
├── entity
├── dto
├── vo
├── common
├── common.validation
├── config
└── exception
```

规则：

1. Controller 放在 `controller`。
2. Service 接口放在 `service`。
3. Service 实现放在 `service.impl`。
4. MyBatis Mapper 接口放在 `mapper`。
5. 数据库映射对象放在 `entity`。
6. 请求对象放在 `dto`。
7. 响应对象放在 `vo`。
8. 通用响应、错误码等放在 `common`。
9. 配置类放在 `config`。
10. 异常类和全局异常处理放在 `exception`。

## 四、分层规则

后端调用链固定为：

```text
Controller -> Service -> Mapper -> Database
```

Controller：

1. 接收 HTTP 请求。
2. 接收 DTO 或简单查询参数。
3. 使用 `@Valid` 或 `@Validated` 触发参数校验。
4. 调用 Service。
5. 返回统一响应结构。

Controller 禁止：

1. 直接调用 Mapper。
2. 直接操作数据库。
3. 编写复杂业务逻辑。
4. 直接返回 Entity。

Service：

1. 处理业务规则。
2. 调用 Mapper。
3. 涉及多次数据库写操作时使用事务。
4. 对业务错误抛出 `BusinessException`。

Service 禁止：

1. 调用 Controller。
2. 返回数据库表结构给前端。
3. 编写前端展示逻辑。

Mapper：

1. 只负责数据库访问。
2. Mapper 接口放在 `com.example.employee.mapper`。
3. SQL 使用 XML 或 MyBatis 注解时，必须与 `docs/design/database.md` 定义字段一致。

Mapper 禁止：

1. 编写业务逻辑。
2. 调用 Service。
3. 处理 HTTP 请求。

## 五、DTO、VO、Entity

DTO：

1. 表示前端请求数据。
2. 放在 `dto` 包。
3. 使用 Jakarta Validation 注解描述基础参数规则。
4. 不包含数据库专用字段，除非接口规范明确需要。

VO：

1. 表示返回给前端的数据。
2. 放在 `vo` 包。
3. 字段必须以 `docs/specs/api.md` 为准。
4. 不直接暴露内部数据库结构。

Entity：

1. 表示数据库表映射。
2. 放在 `entity` 包。
3. 字段必须来自 `docs/design/database.md`。
4. 只在 Mapper 和 Service 内部使用，不直接返回给 Controller 响应。

## 六、统一响应

所有后端接口统一返回：

```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

规则：

1. 成功响应使用 `code = 0`。
2. 成功响应使用 `message = success`。
3. 参数错误使用 `code = 400`。
4. 数据不存在使用 `code = 404`。
5. 系统错误使用 `code = 500`。
6. Controller 不手写响应 Map，必须使用统一响应对象。

## 七、异常处理

后端必须使用全局异常处理。

必须覆盖：

1. 参数校验异常。
2. 业务异常。
3. 未预期系统异常。

规则：

1. 参数错误返回明确提示。
2. 业务错误由 Service 抛出 `BusinessException`。
3. 系统错误只返回通用提示，不返回堆栈、SQL、类名或内部实现细节。
4. 异常响应仍必须使用统一响应结构。

## 八、参数校验

后端使用 Jakarta Validation。

规则：

1. DTO 字段使用 `@NotNull`、`@NotBlank`、`@Size`、`@Email`、`@Min`、`@Max` 等注解。
2. Controller 接收请求体时使用 `@Valid`。
3. Controller 校验查询参数或路径参数时使用 `@Validated`。
4. 复杂业务校验放在 Service，不放在 Controller。
5. 校验失败统一由全局异常处理返回 `code = 400`。

## 九、数据库连接配置

数据库连接配置位于：

```text
backend/src/main/resources/application.yml
```

必须支持环境变量覆盖：

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

规则：

1. 不允许在代码中写死数据库密码。
2. 文档示例可以使用占位值，但不能把真实密码提交到仓库。
3. 默认配置只用于本地开发。
4. 启动失败时不得在响应中返回数据库连接细节。

## 十、MyBatis 规则

基础配置：

1. Mapper XML 扫描路径为 `classpath*:mapper/**/*.xml`。
2. Mapper 接口扫描包为 `com.example.employee.mapper`。
3. 开启下划线转驼峰映射。
4. Entity 类型别名包为 `com.example.employee.entity`。

SQL 规则：

1. 查询业务表时必须按需求过滤 `deleted = 0`。
2. 删除业务数据使用逻辑删除，不做物理删除。
3. 不使用数据库外键。
4. 不使用 `docs/design/database.md` 未定义的字段。
5. 用户输入必须通过 MyBatis 参数绑定传入，避免 SQL 注入。

## 十一、日志安全

规则：

1. 日志不得打印数据库密码。
2. 日志不得打印 token、cookie、Authorization Header。
3. 系统异常可以记录异常类型和堆栈，不能把敏感请求头或敏感配置拼接进日志。
4. 返回给前端的错误信息不得包含堆栈、SQL 或数据库连接信息。

## 十二、Sprint 1 边界

Sprint 1 只补齐后端基础能力。

禁止实现：

1. 员工 CRUD。
2. 部门业务接口。
3. 前端员工页面。
4. 登录和权限。
