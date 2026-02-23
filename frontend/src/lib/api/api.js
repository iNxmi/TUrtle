export default async function request(url, options = {}) {
    const response = await fetch(url, options);
    if (response.status !== 401)
        return response;

    const refreshResponse = await fetch("/api/auth/refresh", {method: "POST"});
    if (refreshResponse.status !== 204)
        return response;

    return fetch(url, options);
}