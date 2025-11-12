export const ssr = false;

export async function load() {
    const response = await fetch("/api/users");
    const payload = await response.json();

    if (response.status === 403) {
        window.location.replace("/login");
        return {};
    }

    if (response.status !== 200) {
        alert(JSON.stringify(payload, null, 2));
        return {};
    }

    return {page: payload};
}