import request from "$lib/api/api.js"
import {checkAuthorization} from "$lib/utils";
import {dev} from "$app/environment";
import {jwtRefreshPath} from "$lib/backend.js";
import {redirect} from "@sveltejs/kit";

export function create(
    endpoint,
    properties = [],
    initialRSQL
) {

    return async function load({url, params}) {

        const parameters = new URLSearchParams()
        const finalEndpoint = url.searchParams.get('endpoint') || endpoint;
        let endpoints;
        if (!Array.isArray(finalEndpoint)) {
            endpoints = finalEndpoint.split(',');
        } else {
            endpoints = [...finalEndpoint];
        }
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
        if (initialRSQL) {
            parameters.set('rsql', initialRSQL + `${params.id}`)
        }

        let page = [];

        const response = await request(`${endpoints[0]}?${parameters}`);
        checkAuthorization(response, url.pathname);
        page.push(await response.json());

        endpoints.shift();

        for (const endpoint of endpoints) {

            const response = await request(`${endpoint}`);
            checkAuthorization(response, url.pathname);
            const endpointData = await response.json();
            page.push(endpointData);
        }

        if (page.length === 1) {
            page = page[0];
        }
        return {page};
    }

}