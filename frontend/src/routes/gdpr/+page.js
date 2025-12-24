import request from "$lib/api/api.js";

export async function load() {
    const response = await request(`/content/gdpr`);
    const payload = await response.text();

    return {content: payload};
}