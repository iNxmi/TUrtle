export const prerender = false;
export const ssr = false;

export async function load() {
    const response = await fetch("/api/auth/me");
    if (response.status === 403)
        return {user: null};

    const payload = await response.json();
    return {user: payload};
}