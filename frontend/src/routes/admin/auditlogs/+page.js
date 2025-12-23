import {create} from "$lib/page.js"

export const load = create(
    "/auditlogs",
    [
        "id",
        "ipAddress",
        "username",
        "endpoint",
        "httpMethod",
        "createdAt"
    ]
)