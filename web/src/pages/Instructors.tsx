import { useState } from 'react'

type Instructor = {
  id: number
  name: string
  email: string
  phone: string
  hireDate: string
  specialization: string
}

let nextId = 1

export default function Instructors() {
  const [items, setItems] = useState<Instructor[]>([])
  const [form, setForm] = useState<Omit<Instructor, 'id'>>({
    name: '', email: '', phone: '', hireDate: new Date().toISOString().slice(0,10), specialization: ''
  })
  const [editId, setEditId] = useState<number | null>(null)

  function submit() {
    if (editId) {
      setItems((prev) => prev.map((x) => (x.id === editId ? { id: editId, ...form } : x)))
      setEditId(null)
    } else {
      setItems((prev) => [{ id: nextId++, ...form }, ...prev])
    }
    setForm({ name: '', email: '', phone: '', hireDate: new Date().toISOString().slice(0,10), specialization: '' })
  }

  function remove(id: number) {
    setItems((prev) => prev.filter((x) => x.id !== id))
    if (editId === id) setEditId(null)
  }

  function edit(item: Instructor) {
    setForm({ name: item.name, email: item.email, phone: item.phone, hireDate: item.hireDate, specialization: item.specialization })
    setEditId(item.id)
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Manage Instructors</h2>
      <div className="bg-white shadow rounded-lg p-4 grid grid-cols-1 md:grid-cols-5 gap-4">
        <TextField label="Name" value={form.name} onChange={(v) => setForm({ ...form, name: v })} />
        <TextField label="Email" value={form.email} onChange={(v) => setForm({ ...form, email: v })} />
        <TextField label="Phone" value={form.phone} onChange={(v) => setForm({ ...form, phone: v })} />
        <TextField label="Hire Date" type="date" value={form.hireDate} onChange={(v) => setForm({ ...form, hireDate: v })} />
        <TextField label="Specialization" value={form.specialization} onChange={(v) => setForm({ ...form, specialization: v })} />
        <div className="md:col-span-5">
          <button className="px-4 py-2 rounded bg-teal-600 text-white" onClick={submit}>{editId ? 'Update' : 'Add'} Instructor</button>
        </div>
      </div>

      <div className="mt-6 overflow-x-auto bg-white shadow rounded-lg">
        <table className="min-w-full text-sm">
          <thead className="bg-gray-50">
            <tr>
              {['ID','Name','Email','Phone','Hire Date','Specialization','Actions'].map((h) => (
                <th key={h} className="px-3 py-2 text-left font-medium text-gray-600">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {items.map((it) => (
              <tr key={it.id} className="border-t">
                <td className="px-3 py-2">{it.id}</td>
                <td className="px-3 py-2">{it.name}</td>
                <td className="px-3 py-2">{it.email}</td>
                <td className="px-3 py-2">{it.phone}</td>
                <td className="px-3 py-2">{it.hireDate}</td>
                <td className="px-3 py-2">{it.specialization}</td>
                <td className="px-3 py-2 space-x-2">
                  <button className="px-2 py-1 bg-teal-600 text-white rounded" onClick={() => edit(it)}>Edit</button>
                  <button className="px-2 py-1 bg-rose-600 text-white rounded" onClick={() => remove(it.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {items.length === 0 && (
              <tr>
                <td colSpan={7} className="px-3 py-6 text-center text-gray-500">No instructors yet.</td>
              </tr>
            )}
          </tbody>
        </table>
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