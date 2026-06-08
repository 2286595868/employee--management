<template>
  <section class="employee-list-view">
    <div class="page-heading">
      <div>
        <h2>员工管理</h2>
        <p>查看和维护员工信息，并按姓名、部门、状态筛选</p>
      </div>
      <el-button type="primary" @click="handleCreate">新增员工</el-button>
    </div>

    <el-card class="filter-panel" shadow="never">
      <el-form :model="filters" class="filter-form" label-width="76px" @submit.prevent>
        <el-form-item label="姓名">
          <el-input
            v-model="filters.name"
            clearable
            placeholder="请输入员工姓名"
            @keyup.enter="handleSearch"
          />
        </el-form-item>
        <el-form-item label="部门">
          <el-select
            v-model="filters.departmentId"
            clearable
            filterable
            placeholder="全部部门"
            :loading="departmentLoading"
          >
            <el-option
              v-for="department in departments"
              :key="department.id"
              :label="department.name"
              :value="department.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="filters.status" clearable placeholder="全部状态">
            <el-option label="在职" :value="1" />
            <el-option label="离职" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item class="filter-actions">
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-panel" shadow="never">
      <template #header>
        <div class="table-header">
          <span>员工列表</span>
          <span class="total-text">共 {{ pagination.total }} 条</span>
        </div>
      </template>

      <el-table
        v-loading="employeeLoading"
        :data="employees"
        border
        stripe
        empty-text="暂无员工数据"
      >
        <el-table-column prop="employeeNo" label="员工编号" min-width="120">
          <template #default="{ row }">{{ displayValue(row.employeeNo) }}</template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" min-width="100">
          <template #default="{ row }">{{ displayValue(row.name) }}</template>
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="90">
          <template #default="{ row }">{{ formatGender(row.gender) }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" min-width="130">
          <template #default="{ row }">{{ displayValue(row.phone) }}</template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="180">
          <template #default="{ row }">{{ displayValue(row.email) }}</template>
        </el-table-column>
        <el-table-column prop="departmentName" label="部门" min-width="120">
          <template #default="{ row }">{{ displayValue(row.departmentName) }}</template>
        </el-table-column>
        <el-table-column prop="position" label="职位" min-width="140">
          <template #default="{ row }">{{ displayValue(row.position) }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'" disable-transitions>
              {{ formatStatus(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="hireDate" label="入职日期" min-width="120">
          <template #default="{ row }">{{ displayValue(row.hireDate) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <div class="row-actions">
              <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
              <el-button
                link
                type="danger"
                :loading="deletingEmployeeId === row.id"
                :disabled="deletingEmployeeId !== null && deletingEmployeeId !== row.id"
                @click="handleDelete(row)"
              >
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-row">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @current-change="handlePageChange"
          @size-change="handlePageSizeChange"
        />
      </div>
    </el-card>

    <EmployeeFormDialog
      v-model="formDialogVisible"
      :mode="formMode"
      :employee="selectedEmployee"
      :departments="departments"
      :department-loading="departmentLoading"
      :submitting="formSubmitting"
      @submit="handleFormSubmit"
    />
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { fetchDepartments } from '../api/departments'
import {
  createEmployee,
  deleteEmployee,
  fetchEmployees,
  updateEmployee
} from '../api/employees'
import EmployeeFormDialog from '../components/EmployeeFormDialog.vue'

const EMPTY_TEXT = '-'

const filters = reactive({
  name: '',
  departmentId: null,
  status: null
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const departments = ref([])
const employees = ref([])
const departmentLoading = ref(false)
const employeeLoading = ref(false)
const formDialogVisible = ref(false)
const formMode = ref('create')
const selectedEmployee = ref(null)
const formSubmitting = ref(false)
const deletingEmployeeId = ref(null)
let latestEmployeeRequest = 0

function displayValue(value) {
  if (value === null || value === undefined || value === '') {
    return EMPTY_TEXT
  }

  return value
}

function formatGender(gender) {
  const genderMap = {
    0: '未知',
    1: '男',
    2: '女'
  }

  return genderMap[gender] || EMPTY_TEXT
}

function formatStatus(status) {
  const statusMap = {
    0: '离职',
    1: '在职'
  }

  return statusMap[status] || EMPTY_TEXT
}

function buildEmployeeParams() {
  const params = {
    page: pagination.page,
    pageSize: pagination.pageSize
  }
  const name = filters.name.trim()

  if (name) {
    params.name = name
  }

  if (filters.departmentId !== null && filters.departmentId !== undefined && filters.departmentId !== '') {
    params.departmentId = filters.departmentId
  }

  if (filters.status !== null && filters.status !== undefined && filters.status !== '') {
    params.status = filters.status
  }

  return params
}

async function loadDepartments() {
  departmentLoading.value = true

  try {
    departments.value = await fetchDepartments()
  } catch (error) {
    departments.value = []
    ElMessage.error(error.message || '部门列表加载失败')
  } finally {
    departmentLoading.value = false
  }
}

async function loadEmployees() {
  const requestId = latestEmployeeRequest + 1
  latestEmployeeRequest = requestId
  employeeLoading.value = true

  try {
    const data = await fetchEmployees(buildEmployeeParams())

    if (requestId !== latestEmployeeRequest) {
      return
    }

    employees.value = Array.isArray(data.records) ? data.records : []
    pagination.total = Number(data.total) || 0
  } catch (error) {
    if (requestId !== latestEmployeeRequest) {
      return
    }

    employees.value = []
    pagination.total = 0
    ElMessage.error(error.message || '员工列表加载失败')
  } finally {
    if (requestId === latestEmployeeRequest) {
      employeeLoading.value = false
    }
  }
}

function handleSearch() {
  pagination.page = 1
  loadEmployees()
}

function handleReset() {
  filters.name = ''
  filters.departmentId = null
  filters.status = null
  pagination.page = 1
  loadEmployees()
}

function handlePageChange(page) {
  pagination.page = page
  loadEmployees()
}

function handlePageSizeChange(pageSize) {
  pagination.pageSize = pageSize
  pagination.page = 1
  loadEmployees()
}

function handleCreate() {
  formMode.value = 'create'
  selectedEmployee.value = null
  formDialogVisible.value = true
}

function handleEdit(employee) {
  formMode.value = 'edit'
  selectedEmployee.value = { ...employee }
  formDialogVisible.value = true
}

async function handleFormSubmit(payload) {
  if (formSubmitting.value) {
    return
  }

  formSubmitting.value = true

  try {
    if (formMode.value === 'edit') {
      await updateEmployee(selectedEmployee.value.id, payload)
      ElMessage.success('员工信息更新成功')
    } else {
      await createEmployee(payload)
      pagination.page = 1
      ElMessage.success('员工新增成功')
    }

    formDialogVisible.value = false
    await loadEmployees()
  } catch (error) {
    ElMessage.error(error.message || (formMode.value === 'edit' ? '编辑员工失败' : '新增员工失败'))
  } finally {
    formSubmitting.value = false
  }
}

async function handleDelete(employee) {
  if (deletingEmployeeId.value !== null) {
    return
  }

  deletingEmployeeId.value = employee.id

  try {
    await ElMessageBox.confirm(
      `确认删除员工“${employee.name || employee.employeeNo}”（${employee.employeeNo}）吗？`,
      '删除员工',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
  } catch {
    deletingEmployeeId.value = null
    return
  }

  try {
    await deleteEmployee(employee.id)

    if (employees.value.length === 1 && pagination.page > 1) {
      pagination.page -= 1
    }

    ElMessage.success('员工删除成功')
    await loadEmployees()
  } catch (error) {
    ElMessage.error(error.message || '删除员工失败')
  } finally {
    deletingEmployeeId.value = null
  }
}

onMounted(() => {
  loadDepartments()
  loadEmployees()
})
</script>

<style scoped>
.employee-list-view {
  max-width: 1280px;
  margin: 0 auto;
}

.page-heading {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 16px;
}

h2 {
  margin: 0;
  color: #111827;
  font-size: 24px;
  font-weight: 600;
}

.page-heading p {
  margin: 6px 0 0;
  color: #6b7280;
  font-size: 14px;
}

.filter-panel {
  margin-bottom: 16px;
}

.page-heading > .el-button {
  flex: 0 0 auto;
}

.filter-form {
  display: grid;
  grid-template-columns: minmax(220px, 1fr) minmax(220px, 1fr) minmax(180px, 240px) auto;
  gap: 12px 16px;
  align-items: flex-start;
}

.filter-form :deep(.el-form-item) {
  margin-bottom: 0;
}

.filter-form :deep(.el-select) {
  width: 100%;
}

.filter-actions :deep(.el-form-item__content) {
  display: flex;
  flex-wrap: nowrap;
  gap: 8px;
}

.table-panel {
  overflow: hidden;
}

.table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  color: #1f2937;
  font-weight: 600;
}

.total-text {
  color: #6b7280;
  font-size: 13px;
  font-weight: 400;
}

.row-actions {
  display: flex;
  align-items: center;
  gap: 4px;
}

.pagination-row {
  display: flex;
  justify-content: flex-end;
  padding-top: 16px;
  overflow-x: auto;
}

@media (max-width: 960px) {
  .filter-form {
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 640px) {
  .employee-list-view {
    max-width: none;
  }

  .page-heading {
    align-items: center;
  }

  .filter-form {
    grid-template-columns: 1fr;
  }

  .pagination-row {
    justify-content: flex-start;
  }
}
</style>
