# Employee Management

员工管理系统，用于练习 AI Agent 协作开发流程。

## 技术栈

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

## 后端启动

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

数据库连接可以通过环境变量覆盖：

```bash
DB_URL=jdbc:mysql://localhost:3306/employee_management DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

后端测试：

```bash
cd backend
./mvnw test
```

## 前端启动

环境要求：

- Node.js 20+
- npm 10+

启动命令：

```bash
cd frontend
npm install
npm run dev
```

打开 Vite 输出的本地地址，确认基础首页可以显示。

## 数据库初始化

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

## Sprint 0 范围

已完成基础项目初始化，只包含：

- Spring Boot 应用骨架
- Vue 3 应用骨架
- GET /api/health 健康检查接口
- employee 和 department 建表 SQL
- 启动说明

未实现：

- 员工 CRUD
- 部门业务接口
- 登录
- 权限
- Excel 导入导出
