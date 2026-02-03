import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { usersPath } from '$lib/backend';
export async function load({url}){

    const response = await request(usersPath);
    checkAuthorization(response, url.pathname);
    const users = await response.json();

    return {users};
}