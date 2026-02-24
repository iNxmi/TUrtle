import request from "$lib/api/api.js";

export async function load({params}) {
    const booking = await getItemBooking(params.id);
    return {booking: booking};
}

async function getItemBooking(id) {
    const response = await request(`/api/item-bookings/${id}`);
    return await response.json();
}