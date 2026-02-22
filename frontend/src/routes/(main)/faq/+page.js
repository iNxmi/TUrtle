import request from "$lib/api/api.js";
import {faqPath} from '$lib/backend.js'

export async function load() {
    const response = await request(`${faqPath}?rsql=enabled==true`);
    const payload = await response.json();

    return {all: payload};
}