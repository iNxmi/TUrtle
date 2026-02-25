import {ItemBookings, ItemCategories, Items, RoomBookings} from "$lib/api";

export async function load({parent}) {
    const data = await parent();

    const itemBookings = await getItemBookings(data.user.id);
    const roomBookings = await getRoomBookings(data.user.id);

    const items = await getItems();
    const categories = await getItemCategories();
    const access = await getAccess();

    return {
        itemBookings: itemBookings,
        roomBookings: roomBookings,
        items: items,
        categories: categories,
        access: access
    };
}

async function getItemBookings(userId) {
    const response = await ItemBookings.getCollection({
        rsql: `user.id==${userId}`
    })
    return await response.json();
}

async function getRoomBookings(userId) {
    const response = await RoomBookings.getCollection({
        rsql: `user.id==${userId}`
    })
    return await response.json();
}

async function getItems() {
    const response = await Items.getCollection()
    return await response.json();
}

async function getItemCategories() {
    const response = await ItemCategories.getCollection();
    return await response.json();
}

async function getAccess() {
    const response = await RoomBookings.access();
    return await response.json();
}