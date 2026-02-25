import {ItemCategories, Items, ItemBookings} from "$lib/api";

export async function load() {
    const itemBookings = await getItemBookings();
    const categories = await getCategories();
    const items = await getItems();

    return { itemBookings, categories, items};
}

async function getItemBookings(){
    const response = await ItemBookings.getCollection('rsql=status!=COMPLETED,status!=CANCELLED');
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
