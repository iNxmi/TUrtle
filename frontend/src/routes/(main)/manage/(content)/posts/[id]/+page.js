import {Posts} from "$lib/api";

export async function load({params}) {
    const entity = await getPost(params.id);
    return {entity: entity};
}

async function getPost(id) {
    const response = await Posts.get(id);
    return await response.json();
}