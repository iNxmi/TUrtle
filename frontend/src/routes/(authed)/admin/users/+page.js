export const ssr = false;

export async function load({fetch}) {
    const apiResponse = await fetch("/api/users");
    const page = await apiResponse.json();
    return {page};
}