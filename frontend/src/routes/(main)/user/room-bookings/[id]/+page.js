import request from "$lib/api/api.js";

export async function load({params}) {
    const booking = await getBooking(params.id);
    return {booking: booking};
}

async function getBooking(id) {
    const url = `/room-bookings/${id}`;
    const response = await request(url);
    return await response.json();
}