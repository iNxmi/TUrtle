import {RoomBookings} from "$lib/api";

export async function load({params}) {
    const booking = await getBooking(params.id);
    return {booking: booking};
}

async function getBooking(id) {
    const response = await RoomBookings.get(id);
    return await response.json();
}