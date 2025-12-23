import {create} from "$lib/page.js"

export const load = create(
    "/exceptions",
    [
        "id",
        "endpoint",
        "exception",
        "message",
        "createdAt"
    ]
)