import {redirect} from '@sveltejs/kit';

export default async function request(url, options) {
    const endpoint = `/api${url}`;

    const response = await fetch(endpoint, options);
    if (response.status !== 401)
        return response

    //TODO implement proper fix for not being logged in (optional parameter like planned)
    const refreshResponse = await fetch("/api/auth/refresh", {method: "POST"});
    if (refreshResponse.status === 401 && url !== "/auth/me" && url !== "/permissions")
        throw redirect(401, "/");

    return await fetch(endpoint, options);
}
