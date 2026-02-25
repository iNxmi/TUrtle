import {  Users } from "$lib/api";
export async function load(){

    const userResponse = await Users.getCollection();

    let users;
    if(userResponse.ok){
        users = await userResponse.json();
    }
    return{users}

}