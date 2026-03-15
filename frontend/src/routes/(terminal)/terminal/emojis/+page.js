import {Configuration} from "$lib/api";

export async function load() {
    const emojisResponse = await Configuration.value("EMOJIS_ALL")
    const emojis = await emojisResponse.json()

    return {emojis: emojis}
}