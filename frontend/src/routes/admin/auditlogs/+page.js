export async function load({url}) {
    const response = await fetch("/api/auditlogs");
    const payload = await response.json();

    return {page: payload};
}