import {create} from "$lib/page.js"
import {rolesPath} from '$lib/backend.js'

export const load = create(
    rolesPath,
    [
        "id",
        "ipAddress",
        "username",
        "endpoint",
        "httpMethod",
        "createdAt"
    ]
)