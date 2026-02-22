import {create} from "$lib/page.js"
import {faqPath} from '$lib/backend.js';


export const load = create(
    faqPath,
    [
        "id",
        "name",
        "updatedAt",
        "createdAt"
    ],
    ""
)