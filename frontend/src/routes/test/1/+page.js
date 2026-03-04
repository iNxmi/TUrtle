import { request } from "$lib/api/API";

export async function load(){

    const itemBookingResponse = await request('/api/item-bookings/1');
    const itemBooking = await itemBookingResponse.json();

    const userResponse = await request(`/api/users/${itemBooking.userId}`);
    const user = await userResponse.json();

    const itemResponse = await request(`/api/items/${itemBooking.itemId}`);
    const item = await itemResponse.json();

    return {itemBooking, item, user}

}