# ARCHITECTURE.md

## 一、整体架构

本项目采用前后端分离架构。

前端使用 Vue 3，通过 HTTP JSON 调用后端接口。

后端使用 Spring Boot 3，通过 MyBatis 访问 MySQL 数据库。

整体调用链路：

Vue 3 前端 -> Spring Boot 3 后端 -> MyBatis -> MySQL 8

## 二、后端分层

后端固定分层：

Controller -> Service -> Mapper -> Database

### 1. Controller 层

职责：

- 接收 HTTP 请求
- 接收和校验请求参数
- 调用 Service 层
- 返回统一响应结果

禁止：

- Controller 直接调用 Mapper
- Controller 直接操作数据库
- Controller 编写复杂业务逻辑
- Controller 直接返回 Entity

### 2. Service 层

职责：

- 处理业务逻辑
- 控制事务
- 调用 Mapper 层
- 抛出业务异常

禁止：

- Service 调用 Controller
- Service 直接返回数据库表结构给前端
- Service 写和业务无关的前端展示逻辑

### 3. Mapper 层

职责：

- 只负责数据库访问
- 编写 SQL 或调用 MyBatis 方法
- 返回 Entity 或数据库查询结果

禁止：

- Mapper 写业务逻辑
- Mapper 调用 Service
- Mapper 处理 HTTP 请求

### 4. Database 层

职责：

- 保存业务数据
- 通过表结构、索引、唯一约束保证基础数据规则

## 三、DTO、VO、Entity 区别

### DTO

DTO 是前端传给后端的数据对象。

例如：

- EmployeeCreateDTO
- EmployeeUpdateDTO
- EmployeeQueryDTO

### VO

VO 是后端返回给前端的数据对象。

例如：

- EmployeeVO
- DepartmentVO

### Entity

Entity 是数据库表映射对象。

例如：

- Employee
- Department

规则：

- Controller 接收 DTO
- Controller 返回 VO
- Mapper 操作 Entity
- 不允许直接把 Entity 返回给前端

## 四、后端包结构

后端包结构统一如下：

com.example.employee
├── controller
├── service
│   └── impl
├── mapper
├── entity
├── dto
├── vo
├── common
├── config
└── exception

## 五、前端结构

前端目录结构统一如下：

src
├── api
├── views
├── components
├── router
├── stores
└── utils

说明：

- api：封装后端接口请求
- views：页面
- components：通用组件
- router：路由
- stores：状态管理
- utils：工具方法

## 六、统一响应格式

后端接口统一返回：

{
  "code": 0,
  "message": "success",
  "data": {}
}

说明：

- code = 0 表示成功
- code != 0 表示失败
- message 表示提示信息
- data 表示实际返回数据

## 七、异常处理

后端必须使用统一异常处理。

要求：

- 参数错误返回明确提示
- 业务错误返回明确提示
- 系统错误不能把异常堆栈直接返回给前端
- 日志中不能打印密码、token、cookie 等敏感信息

## 八、事务规则

涉及多次数据库写操作时，必须在 Service 层控制事务。

例如：

- 新增员工并写入关联信息
- 删除员工并处理关联数据
- 批量更新员工状态

禁止在 Controller 层控制事务。
