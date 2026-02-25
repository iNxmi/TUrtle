import {ItemBookings} from "$lib/api";

export async function load({params}) {
    const booking = await getItemBooking(params.id);
    return {booking: booking};
}

async function getItemBooking(id) {
    const response = await ItemBookings.get(id);
    return await response.json();
}