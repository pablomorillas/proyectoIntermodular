import { Outlet } from "react-router-dom";

function Content({ titulo }) {
    return (
        <main>
            <section>
                {titulo && (
                    <h1>
                        {titulo}
                    </h1>
                )}
                <Outlet />
            </section>
        </main>
    );
}

export default Content;