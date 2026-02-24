import {RoomBookings} from "$lib/api";

export async function load({params}) {
    const booking = await getRoomBooking(params.id);
    return {booking: booking};
}

async function getRoomBooking(id) {
    const response = await RoomBookings.get(id);
    return await response.json();
}