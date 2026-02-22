import {create} from "$lib/page.js"
import {exceptionsPath} from '$lib/backend.js'

export const load = create(
    exceptionsPath,
    [
        "id",
        "endpoint",
        "exception",
        "message",
        "createdAt"
    ]
)