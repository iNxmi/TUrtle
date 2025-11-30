import request from "$lib/api/api.js";

export async function load({params}) {
    const response = await request(`/support/one/${params.id}`);
    const payload = await response.json();

    return {ticket: payload};
}