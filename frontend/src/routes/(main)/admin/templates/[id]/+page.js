import request from "$lib/api/api.js";
import { templatesPath} from '$lib/backend';

export async function load({params}) {
    const url = templatesPath+`/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {template: payload};
}