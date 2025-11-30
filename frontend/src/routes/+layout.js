import request from "$lib/api/api.js";

export const prerender = false;
export const ssr = false;

export async function load() {
    const profileResponse = await request("/profile");
    let user = null
    if (profileResponse.status === 200)
        user = await profileResponse.json();

    return {user};
}