import request from "$lib/api/api.js";
/* import { checkAuthorization } from "$lib/utils"; */
import {authPath, permissionsPath} from '$lib/backend'

export const prerender = false;
export const ssr = false;

export async function load({url}) {
    let payload = {
        user: null,
        permissions: []
    }

    const permissionsResponse = await request(permissionsPath);
    if (permissionsResponse.ok)
        payload.permissions = await permissionsResponse.json();

    const meResponse = await request(authPath + '/me');
    if (meResponse.ok)
        payload.user = await meResponse.json();

    return payload;
}