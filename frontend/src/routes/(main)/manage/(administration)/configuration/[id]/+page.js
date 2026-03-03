import {Configuration} from "$lib/api";

export async function load({params}) {
    const configuration = await getConfiguration(params.id);
    return {configuration: configuration};
}

async function getConfiguration(id) {
    const response = await Configuration.get(id);
    return await response.json();
}