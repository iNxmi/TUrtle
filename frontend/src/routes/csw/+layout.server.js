// +layout.server.js
import { redirect } from '@sveltejs/kit';
import { publicRoutes } from '$lib/publicRoutes';

export async function load({ locals, url }) {
    const isPublicPage = publicRoutes.some(path =>
        url.pathname === path || url.pathname.startsWith(path + "/")
    );

    if (!isPublicPage && !url.pathname.startsWith("/terminal") && !locals.user) {
        throw redirect(303, `/csw/dashboard`);
    }

    return {
        user: locals.user ?? null
    };
}