import {Roles, Users} from "$lib/api";

export async function load({params}) {
    const entity = await getUser(params.id);
    const roles = await getRoles();
    const statuses = await getStatuses();

    return {
        entity: entity,
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