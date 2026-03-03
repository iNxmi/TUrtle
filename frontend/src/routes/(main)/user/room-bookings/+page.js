import {getPage} from "$lib/utils.js";
import {RoomBookings} from "$lib/api";

export async function load({url, parent}) {
    const data = await parent();
    const user = data.user;

    const page = await getPage(url, "/api/room-bookings", `user.id==${user.id}`)
    const bookings = await getRoomBookings(user.id);
    const access = await getAccess();

    return {
        page: page,
        bookings: bookings,
        access: access
    };
}

async function getAccess() {
    const response = await RoomBookings.access()
    return await response.json();
}

async function getRoomBookings(userId) {
    const response = await RoomBookings.getCollection({
        rsql: `user.id==${userId}`
    })
    return await response.json();
}