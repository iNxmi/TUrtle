import request from "$lib/api/api.js";
import {contentTemplatesPath} from '$lib/backend.js';

export async function load({params}) {
    const url = contentTemplatesPath + `/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {template: payload};
}