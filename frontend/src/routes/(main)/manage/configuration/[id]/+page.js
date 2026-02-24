import request from "$lib/api/api.js";

export async function load({params}) {
    const configuration = await getConfiguration(params.id);
    return {configuration: configuration};
}

async function getConfiguration(id) {
    const response = await request(`/api/configuration/${id}`);
    return await response.json();
}