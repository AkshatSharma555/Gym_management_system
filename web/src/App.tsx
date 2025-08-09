import { NavLink, Route, Routes } from 'react-router-dom'
import Home from './pages/Home'
import Members from './pages/Members'
import Instructors from './pages/Instructors'
import Classes from './pages/Classes'
import Attendance from './pages/Attendance'
import Payments from './pages/Payments'
import Feedback from './pages/Feedback'
import Promotions from './pages/Promotions'

export default function App() {
  return (
    <div className="h-screen w-full flex bg-gray-100">
      <aside className="w-64 bg-gray-900 text-white flex-shrink-0">
        <div className="px-6 py-6 text-2xl font-bold">OP Gym</div>
        <nav className="px-3 space-y-1">
          {[
            { to: '/', label: 'Home' },
            { to: '/members', label: 'Manage Members' },
            { to: '/instructors', label: 'Manage Instructors' },
            { to: '/classes', label: 'Manage Classes' },
            { to: '/attendance', label: 'Attendance' },
            { to: '/payments', label: 'Payments' },
            { to: '/feedback', label: 'Feedback' },
            { to: '/promotions', label: 'Promotions' },
          ].map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              end={item.to === '/'}
              className={({ isActive }) =>
                `block rounded-md px-4 py-2 text-sm transition-colors ${
                  isActive ? 'bg-teal-600' : 'hover:bg-gray-800'
                }`
              }
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
      </aside>
      <main className="flex-1 overflow-y-auto">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/members" element={<Members />} />
          <Route path="/instructors" element={<Instructors />} />
          <Route path="/classes" element={<Classes />} />
          <Route path="/attendance" element={<Attendance />} />
          <Route path="/payments" element={<Payments />} />
          <Route path="/feedback" element={<Feedback />} />
          <Route path="/promotions" element={<Promotions />} />
        </Routes>
      </main>
    </div>
  )
}
