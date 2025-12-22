import request from "$lib/api/api.js";

export async function load({params}) {
    const url = `/roles/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {role: payload};
}