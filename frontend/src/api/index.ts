import axios from 'axios'
import { getToken, clearAuth } from '../store/auth'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
})

api.interceptors.request.use((config) => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

api.interceptors.response.use(
  (response) => {
    const result = response.data
    if (result.code !== 200) {
      return Promise.reject(new Error(result.message || 'Request failed'))
    }
    return result.data
  },
  (error) => {
    if (error.response?.status === 401) {
      clearAuth()
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export interface UserDTO {
  id: number
  username: string
  email: string
  phone: string
  createdAt: string
}

export interface ProductDTO {
  id: number
  name: string
  description: string
  price: number
  stock: number
}

export interface OrderItemDTO {
  productId: number
  productName: string
  quantity: number
  unitPrice: number
}

export interface OrderDTO {
  id: number
  userId: number
  items: OrderItemDTO[]
  totalAmount: number
  status: string
  createdAt: string
}

export const userApi = {
  login: (username: string, password: string) =>
    api.post<unknown, UserDTO>('/user/v1/users/login', { username, password }),
  register: (data: { username: string; password: string; email: string; phone: string }) =>
    api.post<unknown, UserDTO>('/user/v1/users/register', data),
}

export const productApi = {
  list: () => api.get<unknown, ProductDTO[]>('/product/v1/products'),
  getById: (id: number) => api.get<unknown, ProductDTO>(`/product/v1/products/${id}`),
}

export const orderApi = {
  create: (userId: number, items: { productId: number; quantity: number }[]) =>
    api.post<unknown, OrderDTO>('/order/v1/orders', { userId, items }),
  getByUserId: (userId: number) =>
    api.get<unknown, OrderDTO[]>(`/order/v1/orders/user/${userId}`),
}
