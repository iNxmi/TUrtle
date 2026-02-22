import {create} from "$lib/page.js"
import {lockersPath} from '$lib/backend.js';

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