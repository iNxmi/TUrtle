export async function load() {
    const apiResponse = await fetch("/api/auth/me");
    const user = await apiResponse.json();
    return {user};
}