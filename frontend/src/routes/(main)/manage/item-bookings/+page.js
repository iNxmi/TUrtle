import {create} from "$lib/page.js"
import {itemBookingsPath} from '$lib/backend.js'

export const load = create(
    itemBookingsPath,
    [
        "id",
        "updatedAt",
        "createdAt"
    ],
    ""
)