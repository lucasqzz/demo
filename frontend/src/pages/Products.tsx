import { useEffect, useState } from 'react'
import { Table, Button, InputNumber, Modal, message, Tag } from 'antd'
import { ShoppingCartOutlined } from '@ant-design/icons'
import { productApi, orderApi, type ProductDTO } from '../api'
import { getUserId } from '../store/auth'

export default function Products() {
  const [products, setProducts] = useState<ProductDTO[]>([])
  const [loading, setLoading] = useState(false)
  const [buyModal, setBuyModal] = useState<{ visible: boolean; product: ProductDTO | null }>({ visible: false, product: null })
  const [quantity, setQuantity] = useState(1)
  const [ordering, setOrdering] = useState(false)

  const fetchProducts = async () => {
    setLoading(true)
    try {
      const data = await productApi.list()
      setProducts(data)
    } catch (e: unknown) {
      message.error(e instanceof Error ? e.message : 'Failed to load products')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { fetchProducts() }, [])

  const handleBuy = async () => {
    const userId = getUserId()
    if (!userId || !buyModal.product) return
    setOrdering(true)
    try {
      await orderApi.create(userId, [{ productId: buyModal.product.id, quantity }])
      message.success('Order created successfully!')
      setBuyModal({ visible: false, product: null })
      setQuantity(1)
      fetchProducts()
    } catch (e: unknown) {
      message.error(e instanceof Error ? e.message : 'Failed to create order')
    } finally {
      setOrdering(false)
    }
  }

  const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id', width: 60 },
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Description', dataIndex: 'description', key: 'description' },
    { title: 'Price', dataIndex: 'price', key: 'price', render: (v: number) => `¥${v.toFixed(2)}` },
    {
      title: 'Stock', dataIndex: 'stock', key: 'stock',
      render: (v: number) => <Tag color={v > 0 ? 'green' : 'red'}>{v}</Tag>,
    },
    {
      title: 'Action', key: 'action',
      render: (_: unknown, record: ProductDTO) => (
        <Button
          type="primary"
          icon={<ShoppingCartOutlined />}
          disabled={record.stock <= 0}
          onClick={() => { setBuyModal({ visible: true, product: record }); setQuantity(1) }}
        >
          Buy
        </Button>
      ),
    },
  ]

  return (
    <>
      <Table dataSource={products} columns={columns} rowKey="id" loading={loading} />
      <Modal
        title={`Buy: ${buyModal.product?.name}`}
        open={buyModal.visible}
        onOk={handleBuy}
        onCancel={() => setBuyModal({ visible: false, product: null })}
        confirmLoading={ordering}
      >
        <p>Price: ¥{buyModal.product?.price.toFixed(2)}</p>
        <p>Stock: {buyModal.product?.stock}</p>
        <p>
          Quantity: <InputNumber min={1} max={buyModal.product?.stock || 1} value={quantity} onChange={(v) => setQuantity(v || 1)} />
        </p>
        <p style={{ fontWeight: 'bold' }}>
          Total: ¥{((buyModal.product?.price || 0) * quantity).toFixed(2)}
        </p>
      </Modal>
    </>
  )
}
