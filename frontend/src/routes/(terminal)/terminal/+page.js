import request from "$lib/api/api.js";

export async function load() {
    const response = await request(`/configuration/EMOJIS_ALL`);
    const payload = await response.json();

    return {emojis: payload.value};
}