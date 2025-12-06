import request from "$lib/api/api.js";

export async function load({params}) {
    const url = `/users/one?id=${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {user: payload};
}