import {request} from "./API.js";

export class Door {

    static async emojis(emojis) {
        const payload = {emojis: emojis};

        return request("/api/door/emojis", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async unlock() {
        return request("/api/door/unlock", {method: "POST"});
    }

}