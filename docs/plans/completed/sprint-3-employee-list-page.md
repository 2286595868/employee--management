# Sprint 3 Employee List Page

## 一、本次目标

Sprint 3 的目标是实现前端员工列表页面，让用户可以查看员工分页列表，并通过姓名、部门和状态筛选员工。

本 Sprint 只实现前端员工列表页面，不实现新增、编辑、删除、登录、权限或 Excel 相关能力。

需要完成：

1. 员工列表表格。
2. 调用后端分页查询员工接口。
3. 姓名搜索。
4. 部门筛选。
5. 状态筛选。
6. 分页切换。
7. 基础加载态和错误提示。
8. 补充必要的前端验证方式。
9. 更新 `docs/progress.md`。

## 二、本次不做什么

Sprint 3 明确不做以下内容：

1. 不实现新增员工。
2. 不实现编辑员工。
3. 不实现删除员工。
4. 不实现新增弹窗。
5. 不实现编辑弹窗。
6. 不实现删除确认弹窗。
7. 不实现登录。
8. 不实现权限。
9. 不实现 JWT、Session、OAuth 或角色控制。
10. 不实现 Excel 导入。
11. 不实现 Excel 导出。
12. 不修改后端接口。
13. 不修改数据库表结构。
14. 不新增数据库字段。
15. 不引入新的前端框架或状态管理库。
16. 不把后端 Entity 概念暴露到前端页面实现中。

## 三、Sprint Contract

Sprint 3 完成标准如下：

1. 必须实现一个员工管理前端列表页面。
2. 页面必须展示员工列表表格。
3. 表格数据必须来自 `GET /api/employees`。
4. 请求员工列表时必须使用 `page` 和 `pageSize` 查询参数。
5. 默认页码必须为 1。
6. 默认每页数量必须为 10。
7. 页面必须支持按员工姓名搜索。
8. 姓名搜索必须通过 `GET /api/employees` 的 `name` 查询参数实现。
9. 页面必须支持按部门筛选。
10. 部门筛选选项必须来自 `GET /api/departments`。
11. 部门筛选必须通过 `GET /api/employees` 的 `departmentId` 查询参数实现。
12. 页面必须支持按员工状态筛选。
13. 状态筛选只允许选择全部、在职和离职。
14. 状态筛选值必须与 `docs/specs/api.md` 保持一致：`1` 表示在职，`0` 表示离职。
15. 状态筛选必须通过 `GET /api/employees` 的 `status` 查询参数实现。
16. 全部状态、全部部门和空姓名搜索时，不应向后端传递对应空筛选值。
17. 点击搜索后必须从第 1 页重新查询。
18. 点击重置后必须清空姓名、部门和状态筛选，并从第 1 页重新查询。
19. 分页切换页码时必须重新请求员工列表。
20. 分页切换每页数量时必须从第 1 页重新请求员工列表。
21. 页面必须展示后端返回的 `total`。
22. 页面必须展示后端返回的 `records`。
23. 表格至少展示：员工编号、姓名、性别、手机号、邮箱、部门、职位、状态、入职日期。
24. 性别展示必须把 `0`、`1`、`2` 转为用户可读文本：未知、男、女。
25. 状态展示必须把 `1`、`0` 转为用户可读文本：在职、离职。
26. 部门名称必须优先展示 `departmentName`。
27. `departmentName` 为空时，页面必须展示为空占位，不得报错。
28. 可空字段为空时，页面必须使用统一占位展示，不得显示 `null` 或 `undefined`。
29. 员工列表请求期间必须有基础加载态。
30. 部门列表请求失败时必须有基础错误提示，页面不能崩溃。
31. 员工列表请求失败时必须有基础错误提示，页面不能崩溃。
32. 后端返回 `code != 0` 时，前端必须按失败处理并展示 `message` 或通用错误文案。
33. Axios 网络错误或超时时必须展示通用错误文案。
34. 前端 API 调用必须封装在 `src/api` 下。
35. 页面实现必须放在 `src/views` 下。
36. 通用展示组件如确有必要才放在 `src/components` 下。
37. 前端必须遵守 `src/api -> src/views -> src/components` 分层约束。
38. 页面不得直接使用硬编码后端绝对地址，必须复用现有 `src/api/http.js` 或同层封装。
39. 本 Sprint 不允许新增业务后端接口。
40. 本 Sprint 不允许修改 `docs/specs/api.md` 中已定义的接口语义，除非先由 plan-agent 重新规划。
41. 本 Sprint 不允许修改 `docs/design/database.md` 或 `docs/generated/db-schema.md`。
42. 不允许在代码、配置或文档中写死真实数据库密码、JWT 密钥或其他敏感信息。
43. 前端页面必须能通过 README 中的前端启动方式启动。
44. 必须执行 `cd frontend && npm run build` 并记录结果。
45. 必须在后端可用时进行一次前后端联调验证，并记录结果。
46. 联调验证必须至少覆盖：首次加载、姓名搜索、部门筛选、状态筛选、分页页码切换、每页数量切换、重置筛选、员工接口失败提示。
47. `docs/progress.md` 必须由 code-agent 更新 Sprint 3 的完成内容、构建结果、联调验证命令、验证结果和遗留问题。

## 四、接口范围

本 Sprint 前端只允许调用以下后端接口：

1. `GET /api/employees`
2. `GET /api/departments`

除现有健康检查接口外，不允许新增或修改其他业务接口。

## 五、预计修改文件

文档相关：

- `docs/progress.md`

前端 API：

- `frontend/src/api/http.js`
- `frontend/src/api/employees.js`
- `frontend/src/api/departments.js`

前端页面：

- `frontend/src/App.vue`
- `frontend/src/views/HomeView.vue`
- `frontend/src/views/EmployeeListView.vue`

前端样式：

- `frontend/src/style.css`

前端组件：

- `frontend/src/components/AppShell.vue`

说明：

- 以上是预计范围，code-agent 可以按现有代码实际情况少建或调整文件名。
- 如果 `HomeView.vue` 继续作为首页承载员工列表，也必须保持页面语义清晰。
- 如果新增 `EmployeeListView.vue`，`App.vue` 应展示该员工列表页面。
- 当前 `docs/design/frontend.md` 为空文件，本 Sprint 不要求补充前端设计文档；如 code-agent 发现必须补充规范，应先回到 plan-agent 更新计划。
- 不应修改 `backend/`、`scripts/`、`docs/design/database.md`、`docs/generated/db-schema.md` 或 `docs/specs/api.md`。

## 六、风险点

1. 当前前端没有路由配置，员工列表页面应以最小改动接入现有 `App.vue`，避免引入不必要的路由复杂度。
2. 当前 `src/api/http.js` 只配置了 `baseURL: '/api'`，开发联调依赖 Vite 代理或同源部署；code-agent 需要确认本地联调方式，不能硬编码后端地址。
3. 如果 Vite 尚未配置 `/api` 代理，开发环境直接请求可能落到 Vite 服务本身并失败；应优先通过 Vite proxy 解决，而不是在页面里写死 `http://localhost:8080`。
4. 后端统一响应格式需要前端主动判断 `code`，不能只依赖 HTTP 状态码。
5. `status = 0` 是有效筛选值，构造查询参数时不能因为 falsy 判断误删。
6. `departmentId`、`status`、`page`、`pageSize` 的类型在 Element Plus 表单和分页组件中可能是字符串或数字，传参前应保持与后端兼容。
7. 姓名搜索需要 trim，避免只输入空格时向后端传递无意义参数。
8. 部门接口只返回启用且未删除部门，因此筛选选项不应自行拼接禁用部门。
9. 员工记录中 `departmentName` 可能为空，表格展示必须能处理无部门员工。
10. 本 Sprint 不做新增、编辑、删除，但页面如果出现操作列或按钮，容易误导用户；不应展示不可用的新增、编辑、删除入口。
11. 分页切换和筛选请求可能快速连续触发，至少要保证最终页面状态与当前筛选条件一致。
12. 空列表、接口失败、加载态三种状态容易互相覆盖，页面需要有清晰的显示规则。
13. Element Plus 组件默认文案和布局需要适配中文业务场景，不能出现 `null`、`undefined` 或未翻译状态值。
14. Sprint 2 当前状态是待 review-agent 审核；code-agent 开始 Sprint 3 前应确认后端接口最终可用，避免在未稳定接口上叠加前端实现。

## 七、给 code-agent 的任务清单

1. 确认 Sprint 2 后端接口可启动并能返回 `GET /api/employees`、`GET /api/departments`。
2. 检查前端现有结构和启动方式，保持 Vue 3、Vite、Axios、Element Plus 技术栈。
3. 在 `src/api` 下封装员工分页查询方法。
4. 在 `src/api` 下封装部门列表查询方法。
5. 确认或补充开发环境 `/api` 代理配置，避免页面硬编码后端绝对地址。
6. 创建或改造员工列表页面。
7. 实现姓名、部门、状态筛选表单。
8. 实现搜索和重置行为。
9. 实现员工表格字段展示和枚举值文本转换。
10. 实现空值统一占位展示。
11. 实现分页组件，并接入页码和每页数量切换。
12. 实现员工列表加载态。
13. 实现部门列表加载或失败后的基础处理。
14. 实现员工列表失败时的基础错误提示。
15. 确认页面不包含新增、编辑、删除入口。
16. 执行 `cd frontend && npm run build`。
17. 启动后端和前端，完成至少一轮真实联调验证。
18. 更新 `docs/progress.md`，记录 Sprint 3 完成内容、构建结果、联调命令、联调结果和遗留问题。

## 八、验证方式

自动验证：

1. `cd frontend`
2. `npm run build`

启动验证：

1. 启动后端：

```bash
cd backend
DB_URL=jdbc:mysql://localhost:3306/employee_management DB_USERNAME=root DB_PASSWORD=your_password ./mvnw spring-boot:run
```

2. 启动前端：

```bash
cd frontend
npm run dev
```

3. 打开 Vite 输出的本地地址。
4. 确认员工列表页面可以正常显示。

联调验证：

1. 首次打开页面，应请求部门列表和员工分页列表。
2. 默认员工分页请求应等价于 `GET /api/employees?page=1&pageSize=10`。
3. 输入姓名并搜索，应请求带 `name` 的员工分页接口，并回到第 1 页。
4. 选择部门并搜索，应请求带 `departmentId` 的员工分页接口，并回到第 1 页。
5. 选择在职状态并搜索，应请求带 `status=1` 的员工分页接口，并回到第 1 页。
6. 选择离职状态并搜索，应请求带 `status=0` 的员工分页接口，并回到第 1 页。
7. 切换页码，应请求对应 `page` 的员工分页接口。
8. 切换每页数量，应请求对应 `pageSize` 的员工分页接口，并回到第 1 页。
9. 点击重置，应清空筛选条件并重新请求第 1 页。
10. 后端返回空记录时，页面应显示空表格状态而不是报错。
11. 后端不可用或返回 `code != 0` 时，页面应展示基础错误提示。

说明：

- 如果本机 MySQL 仍使用 Sprint 2 记录的 3307 独立实例，启动后端时应使用对应端口的 `DB_URL`。
- 验证命令中的 `DB_PASSWORD=your_password` 只能作为占位示例，code-agent 不得把真实密码写入文档或代码。
