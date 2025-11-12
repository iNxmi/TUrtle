export async function load({params}) {
    const response = await fetch(`/api/support/${params.id}`);
    const payload = await response.json();

    return {ticket: payload};
}