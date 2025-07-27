// src/routes/+page.server.js
const api = process.env.API_BASE ?? 'http://backend:8000';

export async function load({ fetch }) {
    const res = await fetch(`${api}/api`);
    const data = await res.json();

    return {
        message: data.message
    };
}