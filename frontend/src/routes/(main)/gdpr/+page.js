import request from "$lib/api/api.js";
import { contentPath} from '$lib/backend'

export async function load() {
    const response = await request(contentPath+`/gdpr`);
    const payload = await response.text();

    return {content: payload};
}