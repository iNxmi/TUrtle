import {create} from "$lib/page.js"

export const load = create(
    "/roles",
    [
        "id",
        "ipAddress",
        "username",
        "endpoint",
        "httpMethod",
        "createdAt"
    ]
)