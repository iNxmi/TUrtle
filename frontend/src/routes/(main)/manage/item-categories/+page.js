import {create} from '$lib/page.js';
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