import {ItemBookings, Items, Users} from "$lib/api";

export async function load({params}) {
    const booking = await getItemBooking(params.id);
    const items = await getItems();
    const users = await getUsers();
    const statuses = await getItemBookingStatus();

    return {
        booking: booking,
        items: items,
        users: users,
        statuses: statuses
    };
}

async function getUsers() {
    const response = await Users.getCollection();
    return await response.json();
}

async function getItems() {
    const response = await Items.getCollection();
    return await response.json();
}

async function getItemBooking(id) {
    const response = await ItemBookings.get(id);
    return await response.json();
}

async function getItemBookingStatus() {
    const response = await ItemBookings.status();
    return await response.json();
}