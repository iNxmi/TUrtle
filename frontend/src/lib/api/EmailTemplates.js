import {request} from "./API.js";

export class EmailTemplates {

    static async create(payload) {
        return request("/api/email-templates", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async get(id) {
        return request(`/api/email-templates/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/email-templates?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/email-templates/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/email-templates/${id}`, {method: "DELETE"});
    }

}