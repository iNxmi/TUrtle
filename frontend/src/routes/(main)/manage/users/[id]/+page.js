import request from "$lib/api/api.js";
import {usersPath} from '$lib/backend';

export async function load({params}) {
    const url = usersPath + `/${params.id}`;
    const response = await request(url);
    const payload = await response.json();

    return {user: payload};
}