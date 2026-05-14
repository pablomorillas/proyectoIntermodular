import { Outlet } from "react-router-dom";

function Content({ titulo }) {
    return (
        <main>
            <section>
                {titulo && (
                    <h2>
                        {titulo}
                    </h2>
                )}
                <Outlet />
            </section>
        </main>
    );
}

export default Content;
