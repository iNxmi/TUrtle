import {create} from "$lib/page.js"
import {contentTemplatesPath} from '$lib/backend.js';


export const load = create(
    contentTemplatesPath,
    [
        "id",
        "name",
        "description",
        "content",
        "createdAt"
    ]
)