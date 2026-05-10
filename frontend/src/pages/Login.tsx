import { Form, Input, Button, Card, message, Typography } from 'antd'
import { UserOutlined, LockOutlined } from '@ant-design/icons'
import { useNavigate, Link } from 'react-router-dom'
import { userApi } from '../api'
import { setAuth } from '../store/auth'

export default function Login() {
  const navigate = useNavigate()
  const [form] = Form.useForm()

  const onFinish = async (values: { username: string; password: string }) => {
    try {
      const user = await userApi.login(values.username, values.password)
      setAuth(user.id, user.username)
      message.success('Login successful')
      navigate('/products')
    } catch (e: unknown) {
      message.error(e instanceof Error ? e.message : 'Login failed')
    }
  }

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', background: '#f0f2f5' }}>
      <Card title="Login" style={{ width: 400 }}>
        <Form form={form} onFinish={onFinish}>
          <Form.Item name="username" rules={[{ required: true, message: 'Please enter username' }]}>
            <Input prefix={<UserOutlined />} placeholder="Username" />
          </Form.Item>
          <Form.Item name="password" rules={[{ required: true, message: 'Please enter password' }]}>
            <Input.Password prefix={<LockOutlined />} placeholder="Password" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" block>Login</Button>
          </Form.Item>
          <Typography.Text>
            Don't have an account? <Link to="/register">Register</Link>
          </Typography.Text>
        </Form>
      </Card>
    </div>
  )
}
