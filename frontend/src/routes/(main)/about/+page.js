import request from "$lib/api/api.js";
import {contentPath} from '$lib/backend.js';

export async function load() {
    const response = await request(contentPath+`/about`);
    const payload = await response.text();

    return {content: payload};
}