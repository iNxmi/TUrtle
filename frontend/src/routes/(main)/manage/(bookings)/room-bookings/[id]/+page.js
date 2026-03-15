import {RoomBookings, Users} from "$lib/api";

export async function load({params}) {
    const entity = await getRoomBooking(params.id);
    const users = await getUsers();
    const access = await getAccess();
    const status = await getStatus();

    return {
        entity: entity,
        users: users,
        access: access,
        status: status
    };
}

async function getRoomBooking(id) {
    const response = await RoomBookings.get(id);
    return await response.json();
}

async function getAccess() {
    const response = await RoomBookings.access();
    return await response.json();
}

async function getStatus() {
    const response = await RoomBookings.status();
    return await response.json();
}

async function getUsers() {
    const response = await Users.getCollection();
    return await response.json();
}