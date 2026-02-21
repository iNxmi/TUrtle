import request from "$lib/api/api.js";
import {rolesPath} from '$lib/backend';

export async function load({params}) {
    const url = rolesPath + `/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {role: payload};
}