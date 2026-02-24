import request from "$lib/api/api.js";

export async function load({params}) {
    const booking = await getRoomBooking(params.id);
    return {booking: booking};
}

async function getRoomBooking(id) {
    const response = await request(`/api/room-bookings/${id}`);
    return await response.json();
}