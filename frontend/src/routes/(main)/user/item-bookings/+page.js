import {ItemCategories, Items} from "$lib/api";
import {getPage} from "$lib/utils.js";

export async function load({url}) {
    const itemBookings = await getItemBookings();
    const categories = await getCategories();
    const items = await getItems();

    return { itemBookings, categories, items};
}

async function getCategories() {
    const response = await ItemCategories.getCollection();
    return response.json();
}

async function getItems() {
    const response = await Items.getCollection();
    return response.json();
}

async function getCategories() {
    const response = await ItemCategories.getCollection();
    return response.json();
}

async function getItems() {
    const response = await Items.getCollection();
    return response.json();
}