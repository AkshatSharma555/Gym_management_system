import { useState } from 'react'

type Payment = {
  id: number
  memberId: number
  amount: number
  paymentMethod: 'credit_card' | 'cash' | 'bank_transfer' | 'digital_wallet'
  transactionId: string
  paymentDate: string
}

let nextId = 1

export default function Payments() {
  const [items, setItems] = useState<Payment[]>([])
  const [form, setForm] = useState<Omit<Payment, 'id'>>({
    memberId: 0,
    amount: 0,
    paymentMethod: 'credit_card',
    transactionId: '',
    paymentDate: new Date().toISOString().slice(0,10),
  })

  function add() {
    setItems((prev) => [{ id: nextId++, ...form }, ...prev])
    setForm({ memberId: 0, amount: 0, paymentMethod: 'credit_card', transactionId: '', paymentDate: new Date().toISOString().slice(0,10) })
  }

  return (
    <div className="p-6 grid grid-cols-1 lg:grid-cols-3 gap-6">
      <div className="lg:col-span-2 bg-white shadow rounded-lg p-4 grid grid-cols-1 md:grid-cols-2 gap-4">
        <NumberField label="Member ID" value={form.memberId} onChange={(v) => setForm({ ...form, memberId: v })} />
        <NumberField label="Amount" value={form.amount} onChange={(v) => setForm({ ...form, amount: v })} />
        <SelectField label="Payment Method" value={form.paymentMethod} onChange={(v) => setForm({ ...form, paymentMethod: v as any })} options={[['credit_card','Credit Card'],['cash','Cash'],['bank_transfer','Bank Transfer'],['digital_wallet','Digital Wallet']]} />
        <TextField label="Transaction ID" value={form.transactionId} onChange={(v) => setForm({ ...form, transactionId: v })} />
        <TextField label="Payment Date" type="date" value={form.paymentDate} onChange={(v) => setForm({ ...form, paymentDate: v })} />
        <div className="md:col-span-2">
          <button className="px-4 py-2 rounded bg-teal-600 text-white" onClick={add}>Add Payment</button>
        </div>
      </div>

      <div className="bg-white shadow rounded-lg p-4">
        <h3 className="text-lg font-semibold mb-3">Payments</h3>
        <div className="space-y-2 max-h-[400px] overflow-auto">
          {items.map((p) => (
            <div key={p.id} className="text-sm p-3 border rounded">
              <div><span className="font-medium">ID:</span> {p.id}</div>
              <div><span className="font-medium">Member ID:</span> {p.memberId}</div>
              <div><span className="font-medium">Amount:</span> {p.amount}</div>
              <div><span className="font-medium">Method:</span> {p.paymentMethod}</div>
              <div><span className="font-medium">Transaction ID:</span> {p.transactionId}</div>
              <div><span className="font-medium">Date:</span> {p.paymentDate}</div>
            </div>
          ))}
          {items.length === 0 && <div className="text-gray-500">No payments yet.</div>}
        </div>
      </div>
    </div>
  )
}

function TextField({ label, value, onChange, type = 'text' }: {
  label: string
  value: string
  onChange: (v: string) => void
  type?: string
}) {
  return (
    <label className="block">
      <span className="text-sm text-gray-700">{label}</span>
      <input type={type} className="mt-1 w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-teal-500" value={value} onChange={(e) => onChange(e.target.value)} />
    </label>
  )
}

function NumberField({ label, value, onChange }: {
  label: string
  value: number
  onChange: (v: number) => void
}) {
  return (
    <label className="block">
      <span className="text-sm text-gray-700">{label}</span>
      <input type="number" className="mt-1 w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-teal-500" value={value} onChange={(e) => onChange(Number(e.target.value))} />
    </label>
  )
}

function SelectField({ label, value, onChange, options }: {
  label: string
  value: string
  onChange: (v: string) => void
  options: [string, string][]
}) {
  return (
    <label className="block">
      <span className="text-sm text-gray-700">{label}</span>
      <select className="mt-1 w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-teal-500" value={value} onChange={(e) => onChange(e.target.value)}>
        {options.map(([val, label]) => (
          <option key={val} value={val}>{label}</option>
        ))}
      </select>
    </label>
  )
}