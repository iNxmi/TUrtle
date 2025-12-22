import request from "$lib/api/api.js";
import { checkAuthorization } from "$lib/utils";

export async function load({url}) {
    const response = await request("/auditlogs?pageNumber=0");
    checkAuthorization(response, url.pathname);
    const payload = await response.json();

    return {page: payload};
}