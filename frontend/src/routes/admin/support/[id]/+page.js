import request from "$lib/api/api.js";
import { supportTicketsPath} from '$lib/backend'

export async function load({params}) {
    const url = supportTicketsPath+`/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {ticket: payload};
}