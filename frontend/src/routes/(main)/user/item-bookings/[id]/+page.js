import {ItemBookings, Lockers, Items} from "$lib/api";

export async function load({params}) {
    const booking = await getItemBooking(params.id);
    const item = await getItem(booking.itemId);
    const locker = await getLocker(item.lockerId);

    return {
        booking: booking,
        locker: locker
    };
}

async function getItemBooking(id) {
    const response = await ItemBookings.get(id);
    return await response.json();
}

async function getItem(id) {
    const response = await Items.get(id);
    return await response.json();
}

async function getLocker(id) {
    const response = await Lockers.get(id);
    return await response.json();
}