import request from "$lib/api/api.js";

export async function load() {
    const response = await request(`/faq`);
    const payload = await response.json();

    return {all: payload};
}