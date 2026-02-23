import request from "$lib/api/api.js";

export async function load() {
    const response = await request("/api/faq?rsql=enabled==true");
    const payload = await response.json();

    return {all: payload};
}