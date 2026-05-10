import { useEffect, useState } from 'react'
import { Table, Tag, message, Card } from 'antd'
import { orderApi, type OrderDTO } from '../api'
import { getUserId } from '../store/auth'

export default function Orders() {
  const [orders, setOrders] = useState<OrderDTO[]>([])
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const userId = getUserId()
    if (!userId) return
    setLoading(true)
    orderApi.getByUserId(userId)
      .then(setOrders)
      .catch((e: unknown) => message.error(e instanceof Error ? e.message : 'Failed to load orders'))
      .finally(() => setLoading(false))
  }, [])

  const statusColor: Record<string, string> = {
    CREATED: 'blue',
    PAID: 'green',
    CANCELLED: 'red',
  }

  const columns = [
    { title: 'Order ID', dataIndex: 'id', key: 'id', width: 80 },
    {
      title: 'Items', dataIndex: 'items', key: 'items',
      render: (items: OrderDTO['items']) =>
        items.map((item, i) => (
          <div key={i}>{item.productName} x {item.quantity} (¥{item.unitPrice})</div>
        )),
    },
    { title: 'Total', dataIndex: 'totalAmount', key: 'totalAmount', render: (v: number) => `¥${v.toFixed(2)}` },
    {
      title: 'Status', dataIndex: 'status', key: 'status',
      render: (s: string) => <Tag color={statusColor[s] || 'default'}>{s}</Tag>,
    },
    { title: 'Created', dataIndex: 'createdAt', key: 'createdAt' },
  ]

  return (
    <Card title="My Orders">
      <Table dataSource={orders} columns={columns} rowKey="id" loading={loading} />
    </Card>
  )
}
