import {getPage} from "$lib/utils.js";
import {Users, Items} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/item-bookings")
    const users = await getUsers();
    const items = await getItems();

    return {
        page: page,
        users: users,
        items: items
    };
}

async function getUsers() {
    const response = await Users.getCollection();
    return await response.json();
}

async function getItems() {
    const response = await Items.getCollection();
    return await response.json();
}