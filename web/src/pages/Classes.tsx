import { useState } from 'react'

type GymClass = {
  id: number
  className: string
  instructorId: number
  schedule: string // yyyy-MM-dd HH:mm
  duration: number
  maxParticipants: number
  description: string
}

let nextId = 1

export default function Classes() {
  const [items, setItems] = useState<GymClass[]>([])
  const [form, setForm] = useState<Omit<GymClass, 'id'>>({
    className: '', instructorId: 0, schedule: new Date().toISOString().slice(0,16).replace('T',' '), duration: 60, maxParticipants: 20, description: ''
  })

  function add() {
    setItems((prev) => [{ id: nextId++, ...form }, ...prev])
    setForm({ className: '', instructorId: 0, schedule: new Date().toISOString().slice(0,16).replace('T',' '), duration: 60, maxParticipants: 20, description: '' })
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Manage Classes</h2>
      <div className="bg-white shadow rounded-lg p-4 grid grid-cols-1 md:grid-cols-3 gap-4">
        <TextField label="Class Name" value={form.className} onChange={(v) => setForm({ ...form, className: v })} />
        <NumberField label="Instructor ID" value={form.instructorId} onChange={(v) => setForm({ ...form, instructorId: v })} />
        <TextField label="Schedule (yyyy-MM-dd HH:mm)" value={form.schedule} onChange={(v) => setForm({ ...form, schedule: v })} />
        <NumberField label="Duration (minutes)" value={form.duration} onChange={(v) => setForm({ ...form, duration: v })} />
        <NumberField label="Max Participants" value={form.maxParticipants} onChange={(v) => setForm({ ...form, maxParticipants: v })} />
        <TextField label="Description" value={form.description} onChange={(v) => setForm({ ...form, description: v })} />
        <div className="md:col-span-3">
          <button className="px-4 py-2 rounded bg-teal-600 text-white" onClick={add}>Add Class</button>
        </div>
      </div>

      <div className="mt-6 bg-white shadow rounded-lg p-4">
        <h3 className="text-lg font-semibold mb-3">Classes</h3>
        <div className="space-y-2">
          {items.map((c) => (
            <div key={c.id} className="border rounded p-3 text-sm">
              {c.id}: {c.className} (Instructor ID: {c.instructorId})
            </div>
          ))}
          {items.length === 0 && <div className="text-gray-500">No classes yet.</div>}
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