export async function load() {
    const response = await fetch("/api/exceptions");
    const payload = await response.json();

    return {page: payload};
}