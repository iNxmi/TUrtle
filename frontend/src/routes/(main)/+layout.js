import {Altcha, Connection, Auth, Permissions} from "$lib/api";

export const prerender = false;
export const ssr = false;

export async function load() {
    const user = await getUser();
    const permissions = await getPermissions();
    const connection = await getConnection();

    return {
        user: user,
        permissions: permissions,
        connection: connection
    };
}

async function getConnection() {
    const response = await Connection.get();
    return await response.json();
}

async function getPermissions() {
    const response = await Permissions.permissions();
    if (!response.ok)
        return [];

    return await response.json()
}

async function getUser() {
    const response = await Auth.me();
    if (!response.ok)
        return null;

    return await response.json();
}