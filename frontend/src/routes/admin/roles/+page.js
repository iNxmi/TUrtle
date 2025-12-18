import request from "$lib/api/api.js"
import { checkAuthorization } from "$lib/utils";

export async function load({url}) {
    const response = await request(`/roles/page`);
    checkAuthorization(response, url.pathname);
    const payload = await response.json();

    return {page: payload};
}