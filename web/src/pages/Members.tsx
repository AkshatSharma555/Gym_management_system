import { useMemo, useState } from 'react'

type Member = {
  id: number
  name: string
  email: string
  phone: string
  membershipType: 'monthly' | 'yearly' | 'lifetime'
  joinDate: string
  status: 'active' | 'inactive'
  address: string
  dateOfBirth: string
  gender: 'male' | 'female' | 'other'
}

let nextId = 1

export default function Members() {
  const [members, setMembers] = useState<Member[]>([])
  const [selectedId, setSelectedId] = useState<number | null>(null)
  const selected = useMemo(() => members.find((m) => m.id === selectedId) ?? null, [members, selectedId])

  function addMember(member: Omit<Member, 'id'>) {
    setMembers((prev) => [{ id: nextId++, ...member }, ...prev])
  }

  function updateMember(updated: Member) {
    setMembers((prev) => prev.map((m) => (m.id === updated.id ? updated : m)))
  }

  function deleteMember(id: number) {
    setMembers((prev) => prev.filter((m) => m.id !== id))
    if (selectedId === id) setSelectedId(null)
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Manage Members</h2>

      <MemberForm
        key={selected?.id ?? 'new'}
        initial={selected ?? undefined}
        onSubmit={(data) => (selected ? updateMember({ ...data, id: selected.id }) : addMember(data))}
        onClear={() => setSelectedId(null)}
      />

      <div className="mt-6 overflow-x-auto bg-white shadow rounded-lg">
        <table className="min-w-full text-sm">
          <thead className="bg-gray-50">
            <tr>
              {['ID','Name','Email','Phone','Membership Type','Join Date','Status','Address','Date of Birth','Gender','Actions'].map((h) => (
                <th key={h} className="px-3 py-2 text-left font-medium text-gray-600">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {members.map((m) => (
              <tr key={m.id} className="border-t">
                <td className="px-3 py-2">{m.id}</td>
                <td className="px-3 py-2">{m.name}</td>
                <td className="px-3 py-2">{m.email}</td>
                <td className="px-3 py-2">{m.phone}</td>
                <td className="px-3 py-2 capitalize">{m.membershipType}</td>
                <td className="px-3 py-2">{m.joinDate}</td>
                <td className="px-3 py-2 capitalize">{m.status}</td>
                <td className="px-3 py-2">{m.address}</td>
                <td className="px-3 py-2">{m.dateOfBirth}</td>
                <td className="px-3 py-2 capitalize">{m.gender}</td>
                <td className="px-3 py-2 space-x-2">
                  <button className="px-2 py-1 bg-teal-600 text-white rounded" onClick={() => setSelectedId(m.id)}>Edit</button>
                  <button className="px-2 py-1 bg-rose-600 text-white rounded" onClick={() => deleteMember(m.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {members.length === 0 && (
              <tr>
                <td colSpan={11} className="px-3 py-6 text-center text-gray-500">No members yet. Add one above.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

function MemberForm({ initial, onSubmit, onClear }: {
  initial?: Member
  onSubmit: (data: Omit<Member, 'id'> | Member) => void
  onClear: () => void
}) {
  const [form, setForm] = useState<Omit<Member, 'id'>>({
    name: initial?.name ?? '',
    email: initial?.email ?? '',
    phone: initial?.phone ?? '',
    membershipType: (initial?.membershipType ?? 'monthly') as Member['membershipType'],
    joinDate: initial?.joinDate ?? new Date().toISOString().slice(0, 10),
    status: (initial?.status ?? 'active') as Member['status'],
    address: initial?.address ?? '',
    dateOfBirth: initial?.dateOfBirth ?? '2000-01-01',
    gender: (initial?.gender ?? 'other') as Member['gender'],
  })

  function handleChange<K extends keyof typeof form>(key: K, value: (typeof form)[K]) {
    setForm((prev) => ({ ...prev, [key]: value }))
  }

  return (
    <div className="bg-white shadow rounded-lg p-4">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <TextField label="Name" value={form.name} onChange={(v) => handleChange('name', v)} />
        <TextField label="Email" value={form.email} onChange={(v) => handleChange('email', v)} />
        <TextField label="Phone" value={form.phone} onChange={(v) => handleChange('phone', v)} />
        <SelectField label="Membership Type" value={form.membershipType} onChange={(v) => handleChange('membershipType', v as any)} options={[['monthly','Monthly'],['yearly','Yearly'],['lifetime','Lifetime']]} />
        <TextField label="Join Date" type="date" value={form.joinDate} onChange={(v) => handleChange('joinDate', v)} />
        <SelectField label="Status" value={form.status} onChange={(v) => handleChange('status', v as any)} options={[['active','Active'],['inactive','Inactive']]} />
        <TextField label="Address" value={form.address} onChange={(v) => handleChange('address', v)} />
        <TextField label="Date of Birth" type="date" value={form.dateOfBirth} onChange={(v) => handleChange('dateOfBirth', v)} />
        <SelectField label="Gender" value={form.gender} onChange={(v) => handleChange('gender', v as any)} options={[['male','Male'],['female','Female'],['other','Other']]} />
      </div>
      <div className="mt-4 flex gap-2">
        <button className="px-4 py-2 rounded bg-teal-600 text-white" onClick={() => onSubmit(initial ? ({ ...(initial as any), ...form }) : form)}>
          {initial ? 'Update Member' : 'Add Member'}
        </button>
        {initial && (
          <button className="px-4 py-2 rounded bg-gray-200" onClick={onClear}>Clear</button>
        )}
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