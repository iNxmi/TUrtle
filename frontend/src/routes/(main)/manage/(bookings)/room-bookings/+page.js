import {getPage} from "$lib/utils.js";
import {RoomBookings} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/room-bookings")
    const access = await getAccess();
    const status = await getStatus();

    return {
        page: page,
        access: access,
        status: status
    };
}

async function getAccess() {
    const response = await RoomBookings.access();
    return await response.json();
}

async function getStatus() {
    const response = await RoomBookings.status();
    return await response.json();
}