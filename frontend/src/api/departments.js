import http from './http'

export async function fetchDepartments() {
  try {
    const response = await http.get('/departments')
    const result = response.data

    if (!result || result.code !== 0) {
      throw new Error(result?.message || '部门列表加载失败')
    }

    return Array.isArray(result.data) ? result.data : []
  } catch (error) {
    if (error.response?.data?.message) {
      throw new Error(error.response.data.message)
    }

    if (error.message && error.message !== 'Network Error' && error.code !== 'ECONNABORTED') {
      throw error
    }

    throw new Error('部门列表加载失败，请稍后重试')
  }
}
