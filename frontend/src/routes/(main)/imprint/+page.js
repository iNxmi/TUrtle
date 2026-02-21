import request from "$lib/api/api.js";
import {contentPath} from '$lib/backend';

export async function load() {
    const response = await request(contentPath + `/imprint`);
    const payload = await response.text();

    return {content: payload};
}