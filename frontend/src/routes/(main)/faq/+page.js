import {Faq} from "$lib/api";

export async function load() {
    const response = await Faq.getCollection({
        rsql: "enabled==true"
    });
    const payload = await response.json();

    return {all: payload};
}