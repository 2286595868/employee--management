# API Spec

## 一、统一响应格式

所有接口统一返回：

{
  "code": 0,
  "message": "success",
  "data": {}
}

字段说明：

- code：状态码，0 表示成功，非 0 表示失败
- message：提示信息
- data：返回数据

## 二、状态码规则

- 0：成功
- 400：参数错误
- 404：数据不存在
- 500：系统错误

## 三、员工接口

### 1. 分页查询员工

请求方式：

GET /api/employees

请求参数：

- page：页码，默认 1
- pageSize：每页数量，默认 10
- name：员工姓名，模糊查询，可选
- departmentId：部门 ID，可选
- status：员工状态，可选，1 在职，0 离职

示例：

GET /api/employees?page=1&pageSize=10&name=张三&departmentId=1&status=1

返回数据示例：

{
  "code": 0,
  "message": "success",
  "data": {
    "total": 1,
    "records": [
      {
        "id": 1,
        "employeeNo": "E001",
        "name": "张三",
        "gender": 1,
        "phone": "13800000000",
        "email": "zhangsan@example.com",
        "departmentId": 1,
        "departmentName": "技术部",
        "position": "Java后端开发",
        "status": 1,
        "hireDate": "2026-06-01"
      }
    ]
  }
}

### 2. 新增员工

请求方式：

POST /api/employees

请求体示例：

{
  "employeeNo": "E001",
  "name": "张三",
  "gender": 1,
  "phone": "13800000000",
  "email": "zhangsan@example.com",
  "departmentId": 1,
  "position": "Java后端开发",
  "status": 1,
  "hireDate": "2026-06-01"
}

规则：

1. employeeNo 不能为空且不能重复。
2. name 不能为空。
3. gender 只能是 0、1、2。
4. status 只能是 0、1。
5. departmentId 如果不为空，必须存在于 department 表。

### 3. 更新员工

请求方式：

PUT /api/employees/{id}

规则：

1. id 必须存在。
2. employeeNo 不允许重复。
3. 不允许更新 deleted = 1 的员工。

### 4. 删除员工

请求方式：

DELETE /api/employees/{id}

规则：

1. 使用逻辑删除。
2. deleted 设置为 1。
3. 不允许物理删除。
4. 删除不存在的员工时返回 404。

## 四、部门接口

### 1. 查询部门列表

请求方式：

GET /api/departments

规则：

1. 只返回 deleted = 0 的部门。
2. 只返回 status = 1 的启用部门。
3. 按 sort_order 升序排序。
