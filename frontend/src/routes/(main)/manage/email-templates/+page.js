import {create} from "$lib/page.js"
import {emailTemplatesPath} from '$lib/backend';


export const load = create(
    emailTemplatesPath,
    [
        "id",
        "name",
        "description",
        "subject",
        "content",
        "createdAt"
    ],
    ""
)