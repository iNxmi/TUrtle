import {dev} from '$app/environment';
import {authPath, jwtRefreshPath} from '$lib/backend';
import {redirect} from '@sveltejs/kit';

export default async function request(url, options) {
    const prefix = dev ? "dev/api" : "api";
    const endpoint = `/${prefix}${url}`;

    const response = await fetch(endpoint, options);

    //TODO implement proper fix for not being logged in (optional parameter like planned)
    if (response.status !== 401 || url === '/auth/me' || url === "/permissions")
        return response

    const refreshResponse = await fetch(`/${prefix}${jwtRefreshPath}`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'}
    });

    if (refreshResponse.status === 401)
        redirect(307, '/auth/login');

    return await fetch(endpoint, options);
}
