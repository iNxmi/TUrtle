export async function load() {
    const response = await fetch("/api/users");
    const payload = await response.json();

    return {page: payload};
}