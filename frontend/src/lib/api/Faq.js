import {request} from "./API.js";

export class Faq {

    static async create(payload) {
        return request("/api/faq", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/faq/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/faq?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/faq/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/faq/${id}`, {method: "DELETE"});
    }

}