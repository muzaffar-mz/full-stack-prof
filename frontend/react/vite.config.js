import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    allowedHosts: ['http://full-stack-prof-env.eba-yxfcieth.ap-south-1.elasticbeanstalk.com'],
  }
})
