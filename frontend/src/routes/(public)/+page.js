import request from "$lib/api/api";
import { authPath } from '$lib/backend'

export async function load(){

    const response = await request(authPath+'/me');

    let loggedIn = false;
    if(response.ok) loggedIn = true;

    return {loggedIn};
}