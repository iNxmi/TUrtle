import {create} from "$lib/page.js"
import { supportTicketsPath} from '$lib/backend'

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