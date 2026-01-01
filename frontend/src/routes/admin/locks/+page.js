import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";

export async function load({url}){

    const response = await request('/lockers');
    checkAuthorization(response, url.pathname);

    if(response.ok){
        const lockerData = await response.json();
        return {locker: lockerData}
    }
}