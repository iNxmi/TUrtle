import {create} from "$lib/page.js"
import {auditLogsPath} from '$lib/backend'

export const load = create(
    auditLogsPath,
    [
        "id",
        "ipAddress",
        "username",
        "endpoint",
        "httpMethod",
        "createdAt"
    ],
    ""
)