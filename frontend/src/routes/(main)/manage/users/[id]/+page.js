import request from "$lib/api/api.js";

export async function load({params}) {
    const user = await getUser(params.id);
    const roles = await getRoles();

    return {
        user: user,
        roles: roles
    };
}

async function getUser(id) {
    const response = await request(`/users/${id}`);
    return await response.json();
}

async function getRoles() {
    const response = await request("/roles");
    return await response.json();
}