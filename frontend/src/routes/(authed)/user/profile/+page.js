export async function load() {
    const response = await fetch("/api/auth/me");
    const payload = await response.json();
    return {user: payload};
}