import request from "$lib/api/api.js";

export async function load() {
    const response = await request(`/faq/all`);
    const payload = await response.json();

    return {all: payload};
}