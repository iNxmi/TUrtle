import {getPage} from "$lib/utils.js";
import {ItemBookings, ItemCategories, Items} from "$lib/api";

export async function load({url, parent}) {
    const data = await parent();
    const user = data.user;

    const page = await getPage(url, "/api/item-bookings", `user.id==${user.id}`)
    const categories = await getCategories();
    const items = await getItems();
    const bookings = await getItemBookings(user.id);

    return {
        page: page,
        categories: categories,
        items: items,
        bookings: bookings
    };
}

async function getItemBookings(userId) {
    const response = await ItemBookings.getCollection({
        rsql: `user.id==${userId}`
    })
    return await response.json();
}

async function getCategories() {
    const response = await ItemCategories.getCollection();
    return response.json();
}

async function getItems() {
    const response = await Items.getCollection();
    return response.json();
}