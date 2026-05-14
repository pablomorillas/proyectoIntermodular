import './assets/theme.css'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './assets/index.css'
import { BrowserRouter } from 'react-router-dom'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <a href="#main-content" className="skip-link">
      Saltar al contenido principal
    </a>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
)
