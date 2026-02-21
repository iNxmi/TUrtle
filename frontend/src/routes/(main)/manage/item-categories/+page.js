import {create} from '$lib/page';
import {itemCategoriesPath} from "$lib/backend.js";

export const load = create(
    itemCategoriesPath,
    [
        "id",
        "name",
        "updatedAt",
        "createdAt"
    ],
    ""
)