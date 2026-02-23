import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const page = await getPage(url, "/room-bookings")
    return {page: page};
}