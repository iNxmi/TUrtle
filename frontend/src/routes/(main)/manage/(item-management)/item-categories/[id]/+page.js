import {ItemCategories} from "$lib/api";

export async function load({params}) {
    const category = await getItemCategory(params.id);
    return {category: category};
}

async function getItemCategory(id) {
    const response = await ItemCategories.get(id);
    return await response.json()
}