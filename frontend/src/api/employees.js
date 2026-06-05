import http from './http'

export async function fetchEmployees(params) {
  try {
    const response = await http.get('/employees', { params })
    const result = response.data

    if (!result || result.code !== 0) {
      throw new Error(result?.message || '员工列表加载失败')
    }

    return result.data || { total: 0, records: [] }
  } catch (error) {
    if (error.response?.data?.message) {
      throw new Error(error.response.data.message)
    }

    if (error.message && error.message !== 'Network Error' && error.code !== 'ECONNABORTED') {
      throw error
    }

    throw new Error('员工列表加载失败，请稍后重试')
  }
}
