import request from "$lib/api/api.js";

export async function load({params}) {
    const response = await request(`/api/roles/${params.id}`);
    const payload = await response.json();

    return {role: payload};
}