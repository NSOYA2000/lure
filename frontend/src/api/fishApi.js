import axios from 'axios'

// 创建axios实例
const apiClient = axios.create({
  baseURL: '/api', // 通过Vite代理到后端
  headers: {
    'Content-Type': 'application/json'
  }
})

// 鱼种API
export const fishApi = {
  // 获取所有鱼种
  getAll() {
    return apiClient.get('/fish')
  },

  // 根据ID获取鱼种
  getById(id) {
    return apiClient.get(`/fish/${id}`)
  },

  // 创建鱼种
  create(fishData) {
    return apiClient.post('/fish', fishData)
  },

  // 更新鱼种
  update(id, fishData) {
    return apiClient.put(`/fish/${id}`, fishData)
  },

  // 删除鱼种
  delete(id) {
    return apiClient.delete(`/fish/${id}`)
  }
}

// 为了方便使用，导出一些常用函数
export const fetchAllFish = async () => {
  const response = await fishApi.getAll()
  return response.data
}

export const fetchFishById = async (id) => {
  const response = await fishApi.getById(id)
  return response.data
}

export const createFish = async (fishData) => {
  const response = await fishApi.create(fishData)
  return response.data
}

export const updateFish = async (id, fishData) => {
  const response = await fishApi.update(id, fishData)
  return response.data
}

export const deleteFish = async (id) => {
  await fishApi.delete(id)
}

export default fishApi