import {Roles, Users} from "$lib/api";

export async function load({params}) {
    const user = await getUser(params.id);
    const roles = await getRoles();
    const statuses = await getStatuses();

    return {
        user: user,
        roles: roles,
        statuses: statuses
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

async function getStatuses() {
    const response = await Users.status();
    return await response.json();
}