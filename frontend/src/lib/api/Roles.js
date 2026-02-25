import {request} from "./API.js";

export class Roles {

    static async create(payload) {
        return request("/api/roles", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async type() {
        return request("/api/roles/enum/type")
    }

    static async get(id) {
        return request(`/api/roles/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/roles?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/roles/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/roles/${id}`, {method: "DELETE"});
    }

}