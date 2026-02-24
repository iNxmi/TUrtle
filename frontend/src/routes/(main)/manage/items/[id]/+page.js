import {Items} from "$lib/api";

export async function load({params}) {
    const item = await getItem(params.id);
    return {item: item};
}

async function getItem(id) {
    const response = await Items.get(id);
    return await response.json();
}