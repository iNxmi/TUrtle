import request from "$lib/api/api.js";

export async function load({params}) {
    const item = await getItem(params.id);
    return {item: item};
}

async function getItem(id) {
    const response = await request(`/api/items/${id}`);
    return await response.json();
}