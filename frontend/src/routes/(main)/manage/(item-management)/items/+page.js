import {getPage} from "$lib/utils.js";
import {ItemCategories, Lockers} from "$lib/api/index.js";

export async function load({url}) {
    const page = await getPage(url, "/api/items")
    const lockers = await getLockers();
    const categories = await getItemCategories();

    return {
        page: page,
        lockers: lockers,
        categories: categories
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