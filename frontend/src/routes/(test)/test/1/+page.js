import {Users, ItemBookings, Items} from "$lib/api";

export async function load(){

    const itemBookingResponse = await ItemBookings.get(1);
    const itemBooking = await itemBookingResponse.json();

    const itemsResponse = await Items.getCollection();
    const items = await itemsResponse.json();

    const usersResponse = await Users.getCollection();
    const users = await usersResponse.json();

    const itemBookingStatusResponse = await ItemBookings.status();
    const itemBookingStatus = await itemBookingStatusResponse.json();

    return {itemBooking, items, users,itemBookingStatus}

}