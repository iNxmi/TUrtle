import {Items, ItemCategories, Lockers} from "$lib/api";

export async function load({params}) {
    const item = await getItem(params.id);
    const categories = await getItemCategories();
    const lockers = await getLockers();

    return {
        item: item,
        categories: categories,
        lockers: lockers
    };
}

async function getLockers() {
    const response = await Lockers.getCollection();
    return await response.json();
}

async function getItemCategories() {
    const response = await ItemCategories.getCollection();
    return await response.json();
}

async function getItem(id) {
    const response = await Items.get(id);
    return await response.json();
}