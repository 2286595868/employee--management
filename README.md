# Employee Management

员工管理系统，用于练习 AI Agent 协作开发流程。

## 项目简介

这是一个前后端分离的员工管理系统，当前重点是后端基础能力和员工管理后端接口的实现与验证。

当前仓库已经完成：

- 后端基础工程和统一响应、异常处理、参数校验、MyBatis 配置
- 员工后端 CRUD
- 部门列表接口，供员工表单下拉使用
- 健康检查接口
- 真实 MySQL 8.4.8-arm64 环境验证

当前仓库尚未完成：

- 前端员工页面
- 登录
- 权限管理
- Excel 导入导出
- 部门管理业务接口

## 当前已完成范围

- `GET /api/health` 健康检查接口
- `GET /api/employees` 员工分页查询
- `POST /api/employees` 新增员工
- `PUT /api/employees/{id}` 更新员工
- `DELETE /api/employees/{id}` 逻辑删除员工
- `GET /api/departments` 查询启用且未删除的部门列表
- 统一响应结构、全局异常处理、参数校验基础能力
- 后端测试和真实 MySQL 8.4.8-arm64 验证

## 当前未完成范围

- 前端员工页面
- 登录和权限
- Excel 导入导出
- 部门新增、更新、删除等部门业务接口

## 技术栈

后端：

- Java 17
- Spring Boot 3
- Maven Wrapper
- MyBatis
- MySQL Driver

前端：

- Vue 3
- Vite
- Axios
- Element Plus

数据库：

- MySQL 8

## 目录结构

```text
.
├── backend
├── frontend
├── scripts
├── docs
│   ├── design
│   ├── generated
│   ├── plans
│   ├── quality
│   └── specs
├── AGENTS.md
├── ARCHITECTURE.md
└── README.md
```

## 后端启动方式

环境要求：

- JDK 17
- Maven Wrapper 已包含在 `backend/`，本机不需要预装 Maven

启动命令：

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

如果需要连接本机 MySQL，可以通过环境变量覆盖：

```bash
DB_URL=jdbc:mysql://localhost:3306/employee_management DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

后端测试：

```bash
cd backend
./mvnw test
```

## 前端启动方式

环境要求：

- Node.js 20+
- npm 10+

启动命令：

```bash
cd frontend
npm install
npm run dev
```

打开 Vite 输出的本地地址即可查看前端基础页面。

## MySQL 数据库初始化方式

SQL 文件：

```text
scripts/init-database.sql
```

执行示例：

```bash
mysql -u root -p < scripts/init-database.sql
```

当前表结构快照见：

```text
docs/generated/db-schema.md
```

说明：

- 数据库使用 MySQL 8
- `employee` 和 `department` 表通过 `scripts/init-database.sql` 初始化
- 逻辑删除通过 `deleted` 字段实现

## 当前开发进度

- Sprint 2 已完成员工后端 CRUD 和部门列表接口
- Sprint 2 已完成 `./mvnw test` 验证
- Sprint 2 已完成真实 MySQL 8.4.8-arm64 验证
- 当前状态是等待 review-agent 复审

## AI Agent 协作流程说明

本项目按三类 Agent 协作：

1. `plan-agent`
   - 拆解需求
   - 制定 Sprint Contract
   - 更新 `docs/plans/active/`
   - 必要时更新设计和规格文档

2. `code-agent`
   - 按 Sprint Contract 实现代码
   - 编写必要测试
   - 更新 `docs/progress.md`

3. `review-agent`
   - 审核代码是否符合规范
   - 检查 bug、安全问题、测试缺失
   - 输出给 code-agent 的修改清单

协作顺序通常是：

1. 先由 `plan-agent` 明确 Sprint Contract
2. 再由 `code-agent` 按合同实现
3. 最后由 `review-agent` 审核并给出修改清单

## 参考文档

- [ARCHITECTURE.md](ARCHITECTURE.md)
- [docs/specs/product.md](docs/specs/product.md)
- [docs/specs/api.md](docs/specs/api.md)
- [docs/design/database.md](docs/design/database.md)
- [docs/progress.md](docs/progress.md)
