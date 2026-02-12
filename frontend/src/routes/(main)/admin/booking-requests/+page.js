import request from "$lib/api/api";
import { checkAuthorization } from "$lib/utils";
import { usersPath, deviceBookingsPath } from '$lib/backend';
export async function load({url}){

    const response = await request(usersPath);
    checkAuthorization(response, url.pathname);
    const users = await response.json();

    const itemBookingResponse = await request(deviceBookingsPath+'?rsql=status==REQUESTED');

    let itemBookings;
    if(itemBookingResponse.ok){
        itemBookings = await itemBookingResponse.json();
    }

    return {users, page: itemBookings};
}