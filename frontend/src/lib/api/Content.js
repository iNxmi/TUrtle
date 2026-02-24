import {request} from "./API.js";

export class Content {

    static async about() {
         return request("/api/content/about");
    }

    static async contact() {
         return request("/api/content/contact");
    }

    static async gdpr() {
         return request("/api/content/gdpr");
    }

    static async imprint() {
         return request("/api/content/imprint");
    }

    static async tos() {
         return request("/api/content/tos");
    }

}