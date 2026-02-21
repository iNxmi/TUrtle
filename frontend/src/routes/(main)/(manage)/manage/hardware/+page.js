import request from "$lib/api/api.js";
import {checkAuthorization} from "$lib/utils.js";
import {lockersPath} from '$lib/backend.js'

export async function load({url}) {

    const response = await request(lockersPath);
    checkAuthorization(response, url.pathname);

    if (response.ok) {
        const lockerData = await response.json();
        return {lockers: lockerData}
    }
}