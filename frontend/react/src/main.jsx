import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { ChakraProvider } from '@chakra-ui/react'
import { createStandaloneToast } from '@chakra-ui/react'
import App from './App.jsx'

const { ToastContainer } = createStandaloneToast()


createRoot(document.getElementById('root')).render(
  <StrictMode>
      <ChakraProvider>
          <App />
          <ToastContainer/>
      </ChakraProvider>
  </StrictMode>,
)
