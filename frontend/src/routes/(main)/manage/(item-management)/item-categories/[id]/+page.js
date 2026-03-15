import {ItemCategories} from "$lib/api";

export async function load({params}) {
    const entity = await getItemCategory(params.id);
    return {entity: entity};
}

async function getItemCategory(id) {
    const response = await ItemCategories.get(id);
    return await response.json()
}