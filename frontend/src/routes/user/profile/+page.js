import request from "$lib/api/api.js";

export async function load() {
    const response = await request("/auth/me");
    const payload = await response.json();

    return {user: payload};
}