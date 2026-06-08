<template>
  <el-dialog
    :model-value="modelValue"
    :title="dialogTitle"
    width="min(640px, calc(100vw - 32px))"
    destroy-on-close
    :close-on-click-modal="!submitting"
    :close-on-press-escape="!submitting"
    :show-close="!submitting"
    @close="handleClose"
    @update:model-value="handleVisibleChange"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      class="employee-form"
      label-width="92px"
      @submit.prevent
    >
      <el-form-item label="员工编号" prop="employeeNo">
        <el-input v-model.trim="form.employeeNo" maxlength="32" placeholder="请输入员工编号" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model.trim="form.name" maxlength="50" placeholder="请输入员工姓名" />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="form.gender" placeholder="请选择性别">
          <el-option label="未知" :value="0" />
          <el-option label="男" :value="1" />
          <el-option label="女" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model.trim="form.phone" maxlength="20" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model.trim="form.email" maxlength="100" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="部门" prop="departmentId">
        <el-select
          v-model="form.departmentId"
          clearable
          filterable
          placeholder="请选择部门"
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
      <el-form-item label="职位" prop="position">
        <el-input v-model.trim="form.position" maxlength="50" placeholder="请输入职位" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择状态">
          <el-option label="在职" :value="1" />
          <el-option label="离职" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期" prop="hireDate">
        <el-date-picker
          v-model="form.hireDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择入职日期"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button :disabled="submitting" @click="handleCancel">取消</el-button>
      <el-button type="primary" :loading="submitting" @click="handleSubmit">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, nextTick, reactive, ref, watch } from 'vue'

const props = defineProps({
  modelValue: {
    type: Boolean,
    required: true
  },
  mode: {
    type: String,
    default: 'create',
    validator: (value) => ['create', 'edit'].includes(value)
  },
  employee: {
    type: Object,
    default: null
  },
  departments: {
    type: Array,
    default: () => []
  },
  departmentLoading: {
    type: Boolean,
    default: false
  },
  submitting: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:modelValue', 'submit'])

const formRef = ref(null)
const form = reactive(createDefaultForm())

const dialogTitle = computed(() => (props.mode === 'edit' ? '编辑员工' : '新增员工'))

const requiredSelectValidator = (message, allowedValues) => (_rule, value, callback) => {
  if (!allowedValues.includes(value)) {
    callback(new Error(message))
    return
  }

  callback()
}

const optionalEmailValidator = (_rule, value, callback) => {
  if (!value) {
    callback()
    return
  }

  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailPattern.test(value)) {
    callback(new Error('邮箱格式不正确'))
    return
  }

  callback()
}

const requiredTrimmedValidator = (message) => (_rule, value, callback) => {
  if (!String(value || '').trim()) {
    callback(new Error(message))
    return
  }

  callback()
}

const rules = {
  employeeNo: [
    { validator: requiredTrimmedValidator('请输入员工编号'), trigger: 'blur' },
    { max: 32, message: '员工编号长度不能超过32', trigger: 'blur' }
  ],
  name: [
    { validator: requiredTrimmedValidator('请输入员工姓名'), trigger: 'blur' },
    { max: 50, message: '员工姓名长度不能超过50', trigger: 'blur' }
  ],
  gender: [
    {
      validator: requiredSelectValidator('请选择性别', [0, 1, 2]),
      trigger: 'change'
    }
  ],
  phone: [{ max: 20, message: '手机号长度不能超过20', trigger: 'blur' }],
  email: [
    { max: 100, message: '邮箱长度不能超过100', trigger: 'blur' },
    { validator: optionalEmailValidator, trigger: 'blur' }
  ],
  position: [{ max: 50, message: '职位长度不能超过50', trigger: 'blur' }],
  status: [
    {
      validator: requiredSelectValidator('请选择状态', [0, 1]),
      trigger: 'change'
    }
  ]
}

function createDefaultForm() {
  return {
    employeeNo: '',
    name: '',
    gender: 0,
    phone: '',
    email: '',
    departmentId: null,
    position: '',
    status: 1,
    hireDate: ''
  }
}

function resetFormData() {
  Object.assign(form, createDefaultForm())
}

function fillForm(employee) {
  resetFormData()

  if (!employee) {
    return
  }

  form.employeeNo = employee.employeeNo || ''
  form.name = employee.name || ''
  form.gender = [0, 1, 2].includes(employee.gender) ? employee.gender : 0
  form.phone = employee.phone || ''
  form.email = employee.email || ''
  form.departmentId = employee.departmentId ?? null
  form.position = employee.position || ''
  form.status = [0, 1].includes(employee.status) ? employee.status : 1
  form.hireDate = employee.hireDate || ''
}

function clearValidation() {
  nextTick(() => {
    formRef.value?.clearValidate()
  })
}

function normalizeOptionalString(value) {
  const trimmed = String(value || '').trim()
  return trimmed || null
}

function buildPayload() {
  return {
    employeeNo: form.employeeNo.trim(),
    name: form.name.trim(),
    gender: form.gender,
    phone: normalizeOptionalString(form.phone),
    email: normalizeOptionalString(form.email),
    departmentId: form.departmentId ?? null,
    position: normalizeOptionalString(form.position),
    status: form.status,
    hireDate: form.hireDate || null
  }
}

async function handleSubmit() {
  const valid = await formRef.value?.validate().catch(() => false)

  if (!valid) {
    return
  }

  emit('submit', buildPayload())
}

function handleCancel() {
  emit('update:modelValue', false)
}

function handleClose() {
  resetFormData()
  clearValidation()
}

function handleVisibleChange(value) {
  emit('update:modelValue', value)
}

watch(
  () => props.modelValue,
  (visible) => {
    if (visible) {
      fillForm(props.mode === 'edit' ? props.employee : null)
      clearValidation()
    }
  }
)

watch(
  () => props.employee,
  (employee) => {
    if (props.modelValue && props.mode === 'edit') {
      fillForm(employee)
      clearValidation()
    }
  }
)
</script>

<style scoped>
.employee-form {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 0 16px;
}

.employee-form :deep(.el-form-item) {
  min-width: 0;
}

.employee-form :deep(.el-select),
.employee-form :deep(.el-date-editor) {
  width: 100%;
}

@media (max-width: 640px) {
  .employee-form {
    grid-template-columns: 1fr;
  }
}
</style>
