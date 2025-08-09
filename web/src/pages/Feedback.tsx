import { useState } from 'react'

type FeedbackItem = {
  id: number
  memberId: number
  feedbackText: string
  rating: number
  feedbackDate: string
}

let nextId = 1

export default function Feedback() {
  const [items, setItems] = useState<FeedbackItem[]>([])
  const [form, setForm] = useState<Omit<FeedbackItem, 'id'>>({
    memberId: 0,
    feedbackText: '',
    rating: 5,
    feedbackDate: new Date().toISOString().slice(0,10),
  })

  function submit() {
    setItems((prev) => [{ id: nextId++, ...form }, ...prev])
    setForm({ memberId: 0, feedbackText: '', rating: 5, feedbackDate: new Date().toISOString().slice(0,10) })
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Manage Feedback</h2>

      <div className="bg-white shadow rounded-lg p-4 grid grid-cols-1 md:grid-cols-3 gap-4">
        <NumberField label="Member ID" value={form.memberId} onChange={(v) => setForm({ ...form, memberId: v })} />
        <SelectField label="Rating" value={String(form.rating)} onChange={(v) => setForm({ ...form, rating: Number(v) })} options={[["1","1"],["2","2"],["3","3"],["4","4"],["5","5"]]} />
        <TextField label="Date" type="date" value={form.feedbackDate} onChange={(v) => setForm({ ...form, feedbackDate: v })} />
        <div className="md:col-span-3">
          <label className="block">
            <span className="text-sm text-gray-700">Feedback</span>
            <textarea className="mt-1 w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-teal-500" rows={4} value={form.feedbackText} onChange={(e) => setForm({ ...form, feedbackText: e.target.value })} />
          </label>
        </div>
        <div className="md:col-span-3">
          <button className="px-4 py-2 rounded bg-teal-600 text-white" onClick={submit}>Submit Feedback</button>
        </div>
      </div>

      <div className="mt-6 bg-white shadow rounded-lg p-4">
        <h3 className="text-lg font-semibold mb-3">Existing Feedback</h3>
        <div className="space-y-4">
          {items.map((f) => (
            <div key={f.id} className="border rounded p-3 text-sm">
              <div><span className="font-medium">ID:</span> {f.id}</div>
              <div><span className="font-medium">Member ID:</span> {f.memberId}</div>
              <div><span className="font-medium">Feedback:</span> {f.feedbackText}</div>
              <div><span className="font-medium">Rating:</span> {f.rating}</div>
              <div><span className="font-medium">Date:</span> {f.feedbackDate}</div>
            </div>
          ))}
          {items.length === 0 && <div className="text-gray-500">No feedback yet.</div>}
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