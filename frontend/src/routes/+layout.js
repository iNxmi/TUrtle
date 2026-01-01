import request from "$lib/api/api.js";
import { checkAuthorization } from "$lib/utils";

export const prerender = false;
export const ssr = false;

export async function load() {
    const permissionsResponse = await request("/permissions");
    checkAuthorization(permissionsResponse);
    let permissions = {}
    if (permissionsResponse.status === 200)
        permissions = await permissionsResponse.json();

    const profileResponse = await request("/user/profile");
    let user = {}
    if (profileResponse.status === 200)
        user = await profileResponse.json();

    return {permissions, user};
}