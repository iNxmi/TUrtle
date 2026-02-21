import {create} from "$lib/page.js"
import {faqPath} from '$lib/backend';


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