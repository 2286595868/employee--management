import http from './http'

function handleEmployeeError(error, defaultMessage) {
  if (error.response?.data?.message) {
    throw new Error(error.response.data.message)
  }

  if (!error.response && error.message && error.message !== 'Network Error' && error.code !== 'ECONNABORTED') {
    throw error
  }

  throw new Error(defaultMessage)
}

function assertSuccess(result, defaultMessage) {
  if (!result || result.code !== 0) {
    throw new Error(result?.message || defaultMessage)
  }
}

export async function fetchEmployees(params) {
  try {
    const response = await http.get('/employees', { params })
    const result = response.data

    assertSuccess(result, '员工列表加载失败')

    return result.data || { total: 0, records: [] }
  } catch (error) {
    handleEmployeeError(error, '员工列表加载失败，请稍后重试')
  }
}

export async function createEmployee(payload) {
  try {
    const response = await http.post('/employees', payload)
    assertSuccess(response.data, '新增员工失败')
  } catch (error) {
    handleEmployeeError(error, '新增员工失败，请稍后重试')
  }
}

export async function updateEmployee(id, payload) {
  try {
    const response = await http.put(`/employees/${id}`, payload)
    assertSuccess(response.data, '编辑员工失败')
  } catch (error) {
    handleEmployeeError(error, '编辑员工失败，请稍后重试')
  }
}

export async function deleteEmployee(id) {
  try {
    const response = await http.delete(`/employees/${id}`)
    assertSuccess(response.data, '删除员工失败')
  } catch (error) {
    handleEmployeeError(error, '删除员工失败，请稍后重试')
  }
}
