export const ssr = false;

export async function load({fetch}) {
    const apiResponse = await fetch("/api/auth/me");
    const user = await apiResponse.json();
    return {user};
}