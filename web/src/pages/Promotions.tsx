import { useEffect, useState } from 'react'

export default function Promotions() {
  const [opacity, setOpacity] = useState(0)

  useEffect(() => {
    const t = setInterval(() => setOpacity((o) => Math.min(1, o + 0.05)), 50)
    return () => clearInterval(t)
  }, [])

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Promotions</h2>
      <div className="relative overflow-auto" style={{ height: 400 }}>
        <img src="/PromotionPic.jpg" alt="Promotion" className="w-full h-full object-contain" style={{ opacity }} />
      </div>
    </div>
  )
}