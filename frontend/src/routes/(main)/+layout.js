import {Auth, Configuration, Connection, Permissions} from "$lib/api";

export const prerender = false;
export const ssr = false;

export async function load() {
    const user = await getUser();
    const permissions = await getPermissions();
    const connection = await getConnection();
    const businessHours = await getBusinessHours();

    return {
        user: user,
        permissions: permissions,
        connection: connection,
        businessHours: businessHours
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

async function getBusinessHours() {
    const startResponse = await Configuration.get("DOOR_SCHEDULE_START");
    const endResponse = await Configuration.get("DOOR_SCHEDULE_END");

    if (startResponse.ok && endResponse.ok)
        return {
            start: await startResponse.json(),
            end: await endResponse.json()
        }
}
