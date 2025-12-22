import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
export async function load({url}){

    const response = await request('/roombookings?pageNumber=0');
    checkAuthorization(response, url.pathname);
    const users = await response.json();

    return {users};
}