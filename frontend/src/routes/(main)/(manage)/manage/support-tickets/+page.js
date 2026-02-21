import {create} from "$lib/page.js"
import {supportTicketsPath} from '$lib/backend.js'

export const load = create(
    supportTicketsPath,
    [
        "id",
        "urgency",
        "category",
        "subject",
        "email",
        "createdAt"
    ]
)