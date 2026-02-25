import {request} from "./API.js";

export class SupportTickets {

    static async create(payload) {
        return request("/api/support-tickets", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async category() {
        return request("/api/support-tickets/enum/category")
    }

    static async urgency() {
        return request("/api/support/tickets/enum/urgency")
    }

    static async status() {
        return request("/api/support-tickets/enum/status")
    }

    static async get(id) {
        return request(`/api/support-tickets/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/support-tickets?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/support-tickets/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/support-tickets/${id}`, {method: "DELETE"});
    }

}