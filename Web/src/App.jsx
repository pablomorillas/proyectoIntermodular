import "./assets/theme.css"
import Footer from "./components/Footer.jsx"
import Header from "./components/Header.jsx"
import Router from "./components/Router.jsx"
import { useLocation } from "react-router-dom"

const PATHS_WITHOUT_LAYOUT = new Set(["/login", "/iniciar-sesion", "/register", "/registro"])

function App() {
  const location = useLocation()
  const normalizedPath =
    location.pathname === "/" ? "/" : location.pathname.replace(/\/+$/, "")
  const hideLayout = PATHS_WITHOUT_LAYOUT.has(normalizedPath)

  return (
    <>
      {!hideLayout ? <Header /> : null}
      <Router />
      {!hideLayout ? <Footer /> : null}
    </>
  )
}

export default App
