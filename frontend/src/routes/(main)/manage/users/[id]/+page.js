import {Roles, Users} from "$lib/api";

export async function load({params}) {
    const user = await getUser(params.id);
    const roles = await getRoles();

    return {
        user: user,
        roles: roles
    };
}

async function getUser(id) {
    const response = await Users.get(id);
    return await response.json();
}

async function getRoles() {
    const response = await Roles.getCollection();
    return await response.json();
}