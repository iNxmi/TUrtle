import {create} from "$lib/page.js"
import { templatesPath} from '$lib/backend';


export const load = create(
    templatesPath,
    [
        "id",
        "name",
        "description",
        "content",
        "createdAt"
    ]
)