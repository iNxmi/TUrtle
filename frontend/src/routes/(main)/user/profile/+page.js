import request from "$lib/api/api.js";

export async function load() {
    const roles = await getRoles();
    return {roles: roles};
}

async function getRoles() {
    const response = await request("/api/roles");
    return await response.json();
}