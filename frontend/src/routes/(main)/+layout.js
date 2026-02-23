import request from "$lib/api/api.js";

export const prerender = false;
export const ssr = false;

export async function load() {
    const user = await getUser();
    const permissions = await getPermissions();
    const isTrustedDevice = await getIsTrustedDevice();
    const isLocalNetwork = await getIsLocalNetwork();

    return {
        user: user,
        permissions: permissions,
        isTrustedDevice: isTrustedDevice,
        isLocalNetwork: isLocalNetwork
    };
}

async function getPermissions() {
    const response = await request("/api/permissions");
    if (!response.ok)
        return [];

    return await response.json()
}

async function getUser() {
    const response = await request("/api/auth/me");
    if (!response.ok)
        return null;

    return await response.json();
}

async function getIsTrustedDevice() {
    const response = await request("/api/altcha/trusted");
    if (!response.ok)
        return false;

    const json = await response.json();
    return json.trusted;
}

//TODO implement backend endpoint for checking if local
async function getIsLocalNetwork() {
    return true;
}