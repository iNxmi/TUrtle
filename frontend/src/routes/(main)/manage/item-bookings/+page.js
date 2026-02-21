import {create} from "$lib/page.js"
import {itemsBookingsPath} from '$lib/backend'

export const load = create(
    itemsBookingsPath,
    [
        "id",
        "updatedAt",
        "createdAt"
    ],
    ""
)