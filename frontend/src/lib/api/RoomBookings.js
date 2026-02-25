import {request} from "./API.js";

export class RoomBookings {

    static async create(payload) {
        return request("/api/room-bookings", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async status() {
        return request("/api/room-bookings/enum/status")
    }

    static async access() {
        return request("/api/room-bookings/enum/access")
    }

    static async get(id) {
        return request(`/api/room-bookings/${id}`);
    }

    static async getCollection(parameters) {
        const params = new URLSearchParams(parameters);
        return request(`/api/room-bookings?${params}`);
    }

    static async patch(id, payload) {
        return request(`/api/room-bookings/${id}`, {
            method: "PATCH",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async delete(id) {
        return request(`/api/room-bookings/${id}`, {method: "DELETE"});
    }

}