import request from "$lib/api/api.js";
import {exceptionsPath} from '$lib/backend'

export async function load({params}) {
    const url = exceptionsPath + `/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {exception: payload};
}