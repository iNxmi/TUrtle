import {getPage} from "$lib/utils.js";
import {Roles, Permissions} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/roles")
    const type = await getType();
    const permissions = await getPermissions();

    return {
        page: page,
        type: type,
        permissions: permissions
    };
}

async function getType() {
    const response = await Roles.type();
    return await response.json();
}

async function getPermissions() {
    const response = await Permissions.permission();
    return await response.json();
}