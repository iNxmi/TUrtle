import {create} from "$lib/page.js"
import {lockersPath} from '$lib/backend';

export const load = create(
    lockersPath,
    [
        "id",
        "name",
        "index",
        "updatedAt",
        "createdAt"
    ],
    ""
)