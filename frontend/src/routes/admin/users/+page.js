import request from "$lib/api/api.js"

export async function load({url, fetch}) {
    const parameters = {}

    const page = url.searchParams.get("page")
    if (page)
        parameters.page = page

    const size = url.searchParams.get("size")
    if (size)
        parameters.size = size

    const sort = url.searchParams.get("sort")
    if (sort)
        parameters.sort = sort

    const direction = url.searchParams.get("direction")
    if (direction)
        parameters.direction = direction

    const urlParameters = new URLSearchParams(parameters)

    const response = await request(`/users?${urlParameters.toString()}`);
    const payload = await response.json();

    return {page: payload};
}