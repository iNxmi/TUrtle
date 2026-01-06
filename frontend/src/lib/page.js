import request from "$lib/api/api.js"
import {checkAuthorization} from "$lib/utils";

export function create(
    endpoint,
    properties = []
) {

    return async function load({url}) {

        const parameters = new URLSearchParams()
        const finalEndpoint = url.searchParams.get('endpoint') || endpoint;
        const search = url.searchParams.get("search");
        if (search != null) {
            const rsql = properties.map(property => `${property}=like=${search}`).join(",")
            parameters.set("rsql", rsql)
        }

        const pageNumber = url.searchParams.get("pageNumber") || "0";
        parameters.set("pageNumber", pageNumber)

        const pageSize = url.searchParams.get("pageSize");
        if (pageSize != null)
            parameters.set("pageSize", pageSize)

        const sortProperty = url.searchParams.get("sortProperty");
        if (sortProperty != null)
            parameters.set("sortProperty", sortProperty)

        const sortDirection = url.searchParams.get("sortDirection");
        if (sortDirection != null)
            parameters.set("sortDirection", sortDirection)

        const response = await request(`${finalEndpoint}?${parameters}`);

        checkAuthorization(response, url.pathname);
        const payload = await response.json();

        return {page: payload};
    }

}