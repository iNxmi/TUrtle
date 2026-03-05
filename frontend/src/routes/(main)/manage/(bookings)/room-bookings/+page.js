import {getPage} from "$lib/utils.js";
import {RoomBookings, Users} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/room-bookings");
    const users = await getUsers();
    const access = await getRoomBookingAccess();
    const status = await getRoomBookingStatus();

    return {
        page: page,
        users: users,
        access: access,
        status: status
    };
}

async function getUsers() {
    const response = await Users.getCollection();
    return await response.json();
}

async function getRoomBookingAccess() {
    const response = await RoomBookings.access();
    return await response.json();
}

async function getRoomBookingStatus() {
    const response = await RoomBookings.status();
    return await response.json();
}