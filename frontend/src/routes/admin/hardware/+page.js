import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { lockersPath } from '$lib/backend'

export async function load({url}){

    const response = await request(lockersPath);
    checkAuthorization(response, url.pathname);

    if(response.ok){
        const lockerData = await response.json();
        return {lockers: lockerData}
    }
}