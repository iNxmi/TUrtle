import request from "$lib/api/api.js";

export async function load({params}) {
    const category = await getItemCategory(params.id);
    return {category: category};
}

async function getItemCategory(id) {
    const response = await request(`/api/item-categories/${id}`);
    return await response.json()
}