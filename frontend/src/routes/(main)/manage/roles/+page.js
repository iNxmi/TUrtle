import {create} from "$lib/page.js"
import { rolesPath } from '$lib/backend'

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