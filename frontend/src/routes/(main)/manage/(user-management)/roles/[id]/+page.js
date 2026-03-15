import {Permissions, Roles} from "$lib/api";

export async function load({params}) {
    const entity = await getRole(params.id)
    const permissions = await getPermission()

    return {
        entity: entity,
        permissions: permissions
    };
}

async function getRole(id) {
    const response = await Roles.get(id);
    return await response.json();
}

async function getPermission() {
    const response = await Permissions.permission();
    return await response.json();
}