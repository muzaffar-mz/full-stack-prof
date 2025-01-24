import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { ChakraProvider } from '@chakra-ui/react'
import { createStandaloneToast } from '@chakra-ui/react'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from './App.jsx'
import AuthProvider from "./components/context/AuthContext.jsx";
import Login from "./components/login/Login.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";

const { ToastContainer } = createStandaloneToast()
const router = createBrowserRouter([
    {
        path: "/",
        element: <Login />
    },
    {
        path: "dashboard",
        element: <ProtectedRoute> <App /></ProtectedRoute>
    }
])

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <ChakraProvider>
          <AuthProvider>
              <RouterProvider router={router} />
          </AuthProvider>
          <ToastContainer/>
      </ChakraProvider>
  </StrictMode>,
)
