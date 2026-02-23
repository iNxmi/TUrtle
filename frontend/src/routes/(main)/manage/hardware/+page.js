import request from "$lib/api/api.js";
import {checkAuthorization} from "$lib/utils.js";

export async function load({url}) {

    const response = await request("/api/lockers");
    checkAuthorization(response, url.pathname);

    if (response.ok) {
        const lockerData = await response.json();
        return {lockers: lockerData}
    }
}