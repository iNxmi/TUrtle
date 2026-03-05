import {getPage} from "$lib/utils.js";
import {Users, Items, ItemBookings, ItemCategories} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/item-bookings")
    const users = await getUsers();
    const items = await getItems();
    const categories = await getItemCategories();
    const status = await getItemBookingStatus();

    return {
        page: page,
        users: users,
        items: items,
        status: status,
        categories: categories
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

async function getItemBookingStatus() {
    const response = await ItemBookings.status();
    return await response.json();
}

async function getItemCategories() {
    const response = await ItemCategories.getCollection();
    return await response.json();
}