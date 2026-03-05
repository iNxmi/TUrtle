import {getPage} from "$lib/utils.js";
import {RoomBookings, Users} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/room-bookings");
    const users = await getUsers();

    return {
        page: page,
        users: users
    };
}

async function getUsers() {
    const response = await Users.getCollection();
    return await response.json();
}