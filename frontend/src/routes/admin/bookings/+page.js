import request from "$lib/api/api";
export async function load(){

    const response = await request('/users/all');

    const users = await response.json();

    return {users};
}