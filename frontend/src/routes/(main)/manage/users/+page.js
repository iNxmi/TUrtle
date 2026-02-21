import {create} from "$lib/page.js"
import {usersPath} from '$lib/backend'

export const load = create(
    usersPath,
    [
        "id",
        "username",
        "firstName",
        "lastName",
        "email",
        "createdAt"
    ],
    ""
)