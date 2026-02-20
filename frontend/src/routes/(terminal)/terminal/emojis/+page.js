import request from "$lib/api/api.js";

//TODO fix for prod
export async function load() {
    // const response = await request(`/system-settings/emojis.all`);
    // const payload = await response.json();
    //
    // return {emojis: payload.value};

    const emojis = [
        "😈", "😃", "🎩", "👽", "💩", "❤️",
        "💎", "👂", "👍", "🐋", "🐶", "🐸",
        "❄️", "🎉", "💿", "🍉", "☎️", "🎥",
        "✂️", "⚽", "🚀", "💄", "🌂", "🍄",
        "🍀", "🚗", "🍕", "🍔", "🍨", "💣",
        "🐧", "💼", "🌍", "🐝", "🏠", "⏰"
    ]

    return {emojis}
}