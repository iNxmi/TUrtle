export async function load() {
    const response = await fetch("/api/support");
    const payload = await response.json();

    return {page: payload};
}