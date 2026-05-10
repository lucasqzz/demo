import { BrowserRouter, Routes, Route, Navigate, Link, useNavigate } from 'react-router-dom'
import { Layout, Menu, Button } from 'antd'
import { ShoppingOutlined, OrderedListOutlined, LogoutOutlined } from '@ant-design/icons'
import Login from './pages/Login'
import Register from './pages/Register'
import Products from './pages/Products'
import Orders from './pages/Orders'
import { isAuthenticated, clearAuth, getUsername } from './store/auth'

const { Header, Content } = Layout

function AppLayout() {
  const navigate = useNavigate()
  const username = getUsername()

  const handleLogout = () => {
    clearAuth()
    navigate('/login')
  }

  if (!isAuthenticated()) {
    return <Navigate to="/login" replace />
  }

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header style={{ display: 'flex', alignItems: 'center' }}>
        <div style={{ color: '#fff', fontSize: 18, fontWeight: 'bold', marginRight: 40 }}>
          E-Commerce Demo
        </div>
        <Menu theme="dark" mode="horizontal" style={{ flex: 1 }} items={[
          { key: 'products', icon: <ShoppingOutlined />, label: <Link to="/products">Products</Link> },
          { key: 'orders', icon: <OrderedListOutlined />, label: <Link to="/orders">My Orders</Link> },
        ]} />
        <span style={{ color: '#fff', marginRight: 16 }}>{username}</span>
        <Button type="text" icon={<LogoutOutlined />} onClick={handleLogout} style={{ color: '#fff' }}>
          Logout
        </Button>
      </Header>
      <Content style={{ padding: 24 }}>
        <Routes>
          <Route path="/products" element={<Products />} />
          <Route path="/orders" element={<Orders />} />
          <Route path="*" element={<Navigate to="/products" replace />} />
        </Routes>
      </Content>
    </Layout>
  )
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/*" element={<AppLayout />} />
      </Routes>
    </BrowserRouter>
  )
}
