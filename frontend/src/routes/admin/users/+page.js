import {create} from "$lib/page.js"

export const load = create(
    "/users",
    [
        "id",
        "username",
        "firstName",
        "lastName",
        "email",
        "createdAt"
    ]
)