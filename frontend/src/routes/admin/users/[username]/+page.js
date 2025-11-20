import request from "$lib/api/api.js";

export async function load({params}) {
    const url = `/users/${params.username}`;
    const response = await request(url);
    const payload = await response.json();

    return {user: payload};
}