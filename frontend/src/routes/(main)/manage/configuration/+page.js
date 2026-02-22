import {create} from "$lib/page.js"
import {configurationPath} from '$lib/backend.js'

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