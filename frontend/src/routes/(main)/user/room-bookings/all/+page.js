import {getPage} from "$lib/utils.js";
import {RoomBookings} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/room-bookings")
    const access = await getAccess();

    return {
        page: page,
        access: access
    };
}

async function getAccess() {
    const response = await RoomBookings.access()
    return await response.json();
}