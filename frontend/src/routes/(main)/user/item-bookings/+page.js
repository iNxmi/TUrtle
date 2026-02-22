import request from "$lib/api/api.js";
import {getPage} from "$lib/utils.js";

async function getCategories() {
    const response = await request("/item-categories");
    return response.json();
}

async function getItems() {
    const response = await request("/items");
    return response.json();
}

export async function load({url}) {
    const page = await getPage(url, "/item-bookings")
    const categories = await getCategories();
    const items = await getItems();

    return {
        page: page,
        categories: categories,
        items: items
    };
}