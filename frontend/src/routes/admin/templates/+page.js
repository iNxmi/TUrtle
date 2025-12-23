import {create} from "$lib/page.js"

export const load = create(
    "/templates",
    [
        "id",
        "name",
        "description",
        "content",
        "createdAt"
    ]
)