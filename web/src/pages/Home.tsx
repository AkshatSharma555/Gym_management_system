import { useEffect, useState } from 'react'

const quotes = [
  'Push yourself, because no one else is going to do it for you.',
  'Success starts with self-discipline.',
  'Don’t stop until you’re proud.',
  'Train like a beast, look like a beauty.',
  'The body achieves what the mind believes.',
  'Hustle for that muscle!',
  'Sweat, Smile, Repeat.',
]

export default function Home() {
  const [index, setIndex] = useState(0)

  useEffect(() => {
    const t = setInterval(() => {
      setIndex((i) => (i + 1) % quotes.length)
    }, 5000)
    return () => clearInterval(t)
  }, [])

  return (
    <div
      className="min-h-full bg-cover bg-center"
      style={{ backgroundImage: 'url(/GYMbg.jpg)' }}
    >
      <div className="min-h-full bg-black/50">
        <div className="max-w-5xl mx-auto px-4 py-10">
          <h1 className="text-3xl md:text-4xl font-bold text-white text-center">
            Welcome to OP Gym - Your Fitness Journey Starts Here!
          </h1>
          <p className="mt-10 text-xl md:text-2xl italic text-center text-white transition-opacity duration-500">
            {quotes[index]}
          </p>
        </div>
      </div>
    </div>
  )
}