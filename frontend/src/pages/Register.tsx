import { Form, Input, Button, Card, message, Typography } from 'antd'
import { useNavigate, Link } from 'react-router-dom'
import { userApi } from '../api'

export default function Register() {
  const navigate = useNavigate()
  const [form] = Form.useForm()

  const onFinish = async (values: { username: string; password: string; email: string; phone: string }) => {
    try {
      await userApi.register(values)
      message.success('Registration successful, please login')
      navigate('/login')
    } catch (e: unknown) {
      message.error(e instanceof Error ? e.message : 'Registration failed')
    }
  }

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', background: '#f0f2f5' }}>
      <Card title="Register" style={{ width: 400 }}>
        <Form form={form} onFinish={onFinish}>
          <Form.Item name="username" rules={[{ required: true, message: 'Please enter username' }]}>
            <Input placeholder="Username" />
          </Form.Item>
          <Form.Item name="password" rules={[{ required: true, message: 'Please enter password' }]}>
            <Input.Password placeholder="Password" />
          </Form.Item>
          <Form.Item name="email" rules={[{ required: true, type: 'email', message: 'Please enter valid email' }]}>
            <Input placeholder="Email" />
          </Form.Item>
          <Form.Item name="phone" rules={[{ required: true, message: 'Please enter phone number' }]}>
            <Input placeholder="Phone" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" block>Register</Button>
          </Form.Item>
          <Typography.Text>
            Already have an account? <Link to="/login">Login</Link>
          </Typography.Text>
        </Form>
      </Card>
    </div>
  )
}
