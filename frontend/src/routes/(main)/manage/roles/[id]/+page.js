import request from "$lib/api/api.js";

export async function load({params}) {
    const role = await getRole(params.id)
    return {role: role};
}

async function getRole(id) {
    const response = await request(`/api/roles/${id}`);
    return await response.json();
}