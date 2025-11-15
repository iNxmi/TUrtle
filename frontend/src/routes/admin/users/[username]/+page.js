export async function load({params}) {
    const url = `/api/users/${params.username}`;
    const response = await fetch(url);
    const payload = await response.json();

    return {user: payload};
}