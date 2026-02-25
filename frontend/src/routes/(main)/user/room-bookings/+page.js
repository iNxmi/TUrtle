import {getPage} from "$lib/utils.js";
import {RoomBookings} from "$lib/api";

export async function load({url, parent}) {
    const data = await parent();
    const user = data.user;

    const page = await getPage(url, "/api/room-bookings", `user.id==${user.id}`)
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