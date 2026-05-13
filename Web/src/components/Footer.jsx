import React from "react";

function Footer() {
    return (
        <footer className="bg-gray-800 text-gray-200 py-6 mt-12">
            <div className="container mx-auto px-4 flex flex-col md:flex-row justify-between items-center">
                <div className="mb-4 md:mb-0">
                    <h2 className="text-lg font-bold">Mi Empresa</h2>
                </div>
                <nav className="flex gap-4 mb-4 md:mb-0">
                    <a href="/" className="hover:text-white transition-colors">Inicio</a>
                    <a href="/about" className="hover:text-white transition-colors">Acerca de</a>
                    <a href="/contact" className="hover:text-white transition-colors">Contacto</a>
                </nav>
                <div className="text-sm">
                    &copy; {new Date().getFullYear()} Mi Empresa. Todos los derechos reservados.
                </div>
            </div>
        </footer>
    );
}

export default Footer;
