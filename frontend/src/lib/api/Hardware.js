import {request} from "./API.js";

export class Hardware {

    static async doorEmojis(emojis) {
        const payload = {emojis: emojis};

        return request("/api/hardware/door/emojis", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async doorOpen() {
        return request("/api/hardware/door/open");
    }

    static async lockerOpen(id) {
        return request(`/api/hardware/locker/open?id=${id}`);
    }

}