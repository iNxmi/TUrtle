import { Items,ItemBookings } from "$lib/api"
export async function load(){

    const itemResponse = await Items.getCollection();

    let items;
    if(itemResponse.ok){
        items = await itemResponse.json();
    }

    const itemBookingsResponse = await ItemBookings.getCollection('rsql=status!=CANCELLED,status!=COMPLETED');

    let itemBookings;
    if(itemBookingsResponse.ok){
        itemBookings = await itemBookingsResponse.json();
    }

    return {items, itemBookings}


}