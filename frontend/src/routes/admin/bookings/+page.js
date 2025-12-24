import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
export async function load({url}){

    const response = await request('/users');
    checkAuthorization(response, url.pathname);
    const users = await response.json();

    return {users};
}