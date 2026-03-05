import {ItemBookings, ItemCategories, Items, RoomBookings, Users} from "$lib/api";

export async function load({parent}) {
    const data = await parent();

    const items = await getItems();
    const itemCategories = await getItemCategories();
    const itemBookings = await getItemBookings(data.user.id);

    const roomAccess = await getAccess();
    const roomBookings = await getRoomBookings(data.user.id);

    const users = await getUsers();

    return {
        items: items,
        itemCategories: itemCategories,
        itemBookings: itemBookings,

        roomBookings: roomBookings,
        roomAccess: roomAccess,

        users: users
    };
}

async function getItemBookings(userId) {
    const response = await ItemBookings.getCollection({
        rsql: `user.id==${userId}`
    });
    return await response.json();
}

async function getRoomBookings(userId) {
    const response = await RoomBookings.getCollection({
        rsql: `user.id==${userId}`
    });
    return await response.json();
}

async function getItems() {
    const response = await Items.getCollection();
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

async function getUsers() {
    const response = await Users.getCollection();
    return await response.json();
}