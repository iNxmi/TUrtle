import { redirect } from '@sveltejs/kit';
import jwt from 'jsonwebtoken';
import { publicRoutes } from '$lib/publicRoutes';

const SECRET_KEY = process.env.JWT_SECRET;

export async function handle({ event, resolve }) {
    const token = event.cookies.get("session");
    const clientIP = event.getClientAddress();

    if (token) {
        try {
            const payload = jwt.verify(token, SECRET_KEY);
            event.locals.user = { username: payload.username };
        } catch(err) {
            console.error("Auth check failed", err);
        }
    } else {
        event.locals.user = null;
    }

    const isPublicPage = publicRoutes.some((path) =>
        event.url.pathname.startsWith(path)
    );

    if (!isPublicPage && !url.pathname.startsWith("/terminal") && !locals.user) {
        throw redirect(303, `/csw/dashboard`);
    }

    return resolve(event);
}
