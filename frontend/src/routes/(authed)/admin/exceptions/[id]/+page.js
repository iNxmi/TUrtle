export async function load({params}) {
    const url = `/api/exceptions/${params.id}`;
    const response = await fetch(url);
    const payload = await response.json();

    return {exception: payload};
}