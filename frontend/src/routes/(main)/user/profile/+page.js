import {Roles} from "$lib/api";

export async function load() {
    const roles = await getRoles();
    return {roles: roles};
}

async function getRoles() {
    const response = await Roles.getCollection();
    return await response.json();
}