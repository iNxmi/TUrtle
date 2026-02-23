import request from "$lib/api/api.js";
import {getPage} from "$lib/utils.js";

async function getCategories() {
    const response = await request("/api/item-categories");
    return response.json();
}

async function getItems() {
    const response = await request("/api/items");
    return response.json();
}

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