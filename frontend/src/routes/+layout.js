import request from "$lib/api/api.js";

export const prerender = false;
export const ssr = false;

export async function load() {
    const response = await request("/auth/me");
    if (response.status !== 200)
        return {user: null};

    const payload = await response.json();
    return {user: payload};
}