# AGENTS.md

本项目是一个员工管理前后端网站，用于练习 AI Agent 协作开发流程。

技术栈暂定：

- 后端：Java 17 + Spring Boot 3 + MyBatis + MySQL
- 前端：Vue 3 + Vite + Axios + Element Plus
- 数据库：MySQL 8

## 一、所有 Agent 必须遵守

开始任何任务前，必须先阅读：

- README.md
- ARCHITECTURE.md
- docs/specs/product.md
- docs/specs/api.md
- docs/design/database.md
- docs/progress.md

修改数据库相关内容前，必须阅读：

- docs/design/database.md
- docs/generated/db-schema.md

修改接口前，必须阅读：

- docs/specs/api.md

审核代码前，必须阅读：

- docs/quality/review-checklist.md

## 二、项目分层规则

后端必须遵守：

Controller -> Service -> Mapper -> Database

禁止：

- Controller 直接调用 Mapper
- Controller 直接操作数据库
- Mapper 调用 Service
- Service 调用 Controller
- 直接把 Entity 返回给前端

前端必须遵守：

src/api -> src/views -> src/components

## 三、三个 Subagent 职责

### plan-agent

负责：

- 拆解需求
- 制定 Sprint Contract
- 更新 docs/plans/active 下的计划文件
- 必要时更新 docs/specs 和 docs/design

禁止：

- 直接写业务代码

### code-agent

负责：

- 按 Sprint Contract 写代码
- 编写必要测试
- 更新 docs/progress.md

禁止：

- 擅自扩大需求
- 删除测试来通过构建
- 绕过项目分层
- 写死数据库密码、JWT 密钥等敏感信息

### review-agent

负责：

- 审核代码是否符合规范
- 检查 bug、安全问题、测试缺失
- 判断是否满足 Sprint Contract
- 输出给 code-agent 的修改清单

禁止：

- 直接新增功能
- 无理由重写整个项目

## 四、完成标准

每个 Sprint 完成后必须满足：

1. 符合当前 Sprint Contract
2. 符合 ARCHITECTURE.md
3. 符合 docs/design/database.md
4. 符合 docs/specs/api.md
5. 后端或前端能按文档启动
6. 核心功能有验证方式
7. docs/progress.md 已更新
