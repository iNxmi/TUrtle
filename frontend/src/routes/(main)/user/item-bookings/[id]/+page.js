import request from "$lib/api/api.js";

export async function load({params}) {
    const booking = await getBooking(params.id);
    const item = await getItem(booking.itemId);
    const locker = await getLocker(item.lockerId);

    return {
        booking: booking,
        item: item,
        locker: locker
    };
}

async function getBooking(id) {
    const url = `/api/item-bookings/${id}`;
    const response = await request(url);
    return await response.json();
}

async function getItem(id) {
    const url = `/api/items/${id}`;
    const response = await request(url);
    return await response.json();
}

async function getLocker(id) {
    const url = `/api/lockers/${id}`;
    const response = await request(url);
    return await response.json();
}