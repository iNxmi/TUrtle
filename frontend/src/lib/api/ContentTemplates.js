import {request} from "./API.js";

export class ContentTemplates {

    static async create(payload) {
        return request("/api/content-templates", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async type() {
        return request("/api/content-templates/enum/type")
    }

    static async get(id) {
        return request(`/api/content-templates/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/content-templates?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/content-templates/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/content-templates/${id}`, {method: "DELETE"});
    }

}