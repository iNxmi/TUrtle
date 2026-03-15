import {Configuration} from "$lib/api";

export async function load({params}) {
    const entity = await getConfiguration(params.id);
    return {entity: entity};
}

async function getConfiguration(id) {
    const response = await Configuration.get(id);
    return await response.json();
}