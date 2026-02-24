import request from "$lib/api/api.js";

export async function load({params}) {
    const exception = await getException(params.id);
    return {exception: exception};
}

async function getException(id) {
    const response = await request(`/api/exceptions/${id}`);
    return await response.json();
}