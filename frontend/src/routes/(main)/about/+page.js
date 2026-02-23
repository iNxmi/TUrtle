import request from "$lib/api/api.js";

export async function load() {
    const response = await request("/api/content/about");
    const payload = await response.text();

    return {content: payload};
}