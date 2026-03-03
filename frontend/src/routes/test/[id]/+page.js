import { ItemBookings, Items, Users } from "$lib/api"
export async function load(){

    const itemBookingResponse = await ItemBookings.get(1);

    let itemBooking;
    if(itemBookingResponse.ok){
        itemBooking = await itemBookingResponse.json();
    }

    const itemResponse = await Items.get(itemBooking.itemId);

    let item;

    if(itemResponse.ok){
        item = await itemResponse.json();
    }

    const userResponse = await Users.get(itemBooking.userId);

    let user;
    if(userResponse.ok){
        user = await userResponse.json();
    }

    return { itemBooking, item, user };
}