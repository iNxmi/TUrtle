export async function request(endpoint, options = {}) {
    const response = await fetch(endpoint, options);
    if (response.status !== 401)
        return response;

    const refreshResponse = await fetch("/api/auth/refresh", {method: "POST"});
    if (refreshResponse.status !== 204)
        return response;

    return fetch(endpoint, options);
}