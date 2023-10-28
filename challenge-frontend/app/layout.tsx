import type { Metadata } from 'next'
import './globals.css'

export const metadata: Metadata = {
  title: 'Ensolvers Challenge',
  description: 'Ensolvers Challenge',
  icons: {
    icon: '/favicon.ico',
  }
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body>{children}</body>
    </html>
  )
}
