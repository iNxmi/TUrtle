import request from '$lib/api/api';
import { usersPath } from '$lib/backend.js';

export async function load(){

    const response = await request(usersPath);

    let users = [];
    if(response.ok){

        users = await response.json();
    }

    return {users};
}
