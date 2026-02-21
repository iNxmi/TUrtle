import {create} from "$lib/page.js"
import {configurationPath} from '$lib/backend'

export const load = create(
    configurationPath,
    [
        "id",
        "key",
        "type",
        "value",
        "updatedAt",
        "createdAt"
    ],
    ""
)