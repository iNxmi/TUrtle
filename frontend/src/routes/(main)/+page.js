import {Posts} from "$lib/api";

export async function load() {
    const response = await Posts.getCollection({
        rsql: "enabled==true"
    });
    const payload = await response.json();

    return {all: payload};
}