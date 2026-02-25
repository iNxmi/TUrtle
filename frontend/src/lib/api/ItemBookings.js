import {request} from "./API.js";

export class ItemBookings {

    static async create(payload) {
        return request("/api/item-bookings", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async status() {
        return request("/api/item-bookings/enum/status");
    }

    static async get(id) {
        return request(`/api/item-bookings/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/item-bookings?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/item-bookings/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/item-bookings/${id}`, {method: "DELETE"});
    }

}