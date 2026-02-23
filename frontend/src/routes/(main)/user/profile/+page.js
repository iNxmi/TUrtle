import request from "$lib/api/api.js";

export async function load() {
    const roles = await getRoles();
    return {roles: roles};
}

async function getRoles() {
    const response = await request("/roles");
    const elements = await response.json();
    return Object.fromEntries(elements.map(element => [element.id, element]))
}