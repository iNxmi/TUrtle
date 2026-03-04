import {Roles} from "$lib/api";

export async function load({params}) {
    const role = await getRole(params.id)
    return {role: role};
}

async function getRole(id) {
    const response = await Roles.get(id);
    return await response.json();
}