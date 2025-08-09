import { useState } from 'react'

type Attendance = {
  id: number
  memberId: number
  classId: number
  attendanceDate: string
}

let nextId = 1

export default function Attendance() {
  const [items, setItems] = useState<Attendance[]>([])
  const [form, setForm] = useState<Omit<Attendance, 'id'>>({
    memberId: 0,
    classId: 0,
    attendanceDate: new Date().toISOString().slice(0,10),
  })

  function add() {
    setItems((prev) => [{ id: nextId++, ...form }, ...prev])
    setForm({ memberId: 0, classId: 0, attendanceDate: new Date().toISOString().slice(0,10) })
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Manage Attendance</h2>
      <div className="bg-white shadow rounded-lg p-4 grid grid-cols-1 md:grid-cols-4 gap-4">
        <NumberField label="Member ID" value={form.memberId} onChange={(v) => setForm({ ...form, memberId: v })} />
        <NumberField label="Class ID" value={form.classId} onChange={(v) => setForm({ ...form, classId: v })} />
        <TextField label="Attendance Date" type="date" value={form.attendanceDate} onChange={(v) => setForm({ ...form, attendanceDate: v })} />
        <div className="md:col-span-1 flex items-end">
          <button className="px-4 py-2 rounded bg-teal-600 text-white" onClick={add}>Add Attendance</button>
        </div>
      </div>

      <div className="mt-6 bg-white shadow rounded-lg p-4">
        <h3 className="text-lg font-semibold mb-3">Attendance Records</h3>
        <div className="space-y-2">
          {items.map((a) => (
            <div key={a.id} className="border rounded p-3 text-sm">
              ID: {a.id}, Member ID: {a.memberId}, Class ID: {a.classId}, Date: {a.attendanceDate}
            </div>
          ))}
          {items.length === 0 && <div className="text-gray-500">No attendance yet.</div>}
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