import request from "$lib/api/api.js";

export async function load() {
    const response = await request("/auditlogs");
    const payload = await response.json();

    return {page: payload};
}