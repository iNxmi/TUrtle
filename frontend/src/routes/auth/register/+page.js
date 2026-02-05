import request from "$lib/api/api.js";
import {contentPath} from '$lib/backend'

export async function load() {
    const response = await request("/altcha/trusted");
    const payload = await response.json();

    return {trusted: payload.trusted};
}