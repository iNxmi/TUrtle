import {ItemCategories, Items} from "$lib/api";
import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const page = await getPage(url, "/api/item-bookings")
    const categories = await getCategories();
    const items = await getItems();

    return {
        page: page,
        categories: categories,
        items: items
    };
}

async function getCategories() {
    const response = await ItemCategories.getCollection();
    return response.json();
}

async function getItems() {
    const response = await Items.getCollection();
    return response.json();
}