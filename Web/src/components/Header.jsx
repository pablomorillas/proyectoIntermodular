import Nav from './Nav.jsx';

function Header() {

    return (
        <header className="header-container">
            <div className="header-top">
                <div className="logo-container">
                    {/* Logo Placeholder */}
                    <svg width="50" height="50" viewBox="0 0 50 50" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M10 25L25 10L40 25" stroke="#C81619" strokeWidth="4" strokeLinecap="round" strokeLinejoin="round" />
                        <path d="M10 25V40H25V30H30V40H40V25" stroke="#C81619" strokeWidth="4" strokeLinecap="round" strokeLinejoin="round" />
                        <rect x="5" y="5" width="40" height="40" stroke="#C81619" strokeWidth="2" rx="5" />
                    </svg>
                    {/* <img src="/logo.png" alt="Logo" className="logo-img" /> */}
                </div>

                <div className="search-bar-container">
                    <input type="text" placeholder="Buscar" className="search-input-main" />
                    <svg className="search-icon" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                </div>

                <div className="user-actions">
                    <svg className="user-icon" xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
                </div>
            </div>

            <Nav />
        </header>
    );
}
export default Header;