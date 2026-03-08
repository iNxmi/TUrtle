import {request} from "./API.js";

export class Auth {

    static async login(payload) {
        return request("/api/auth/login", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async register(payload) {
        return request("/api/auth/register", {
            method: "POST",
            body: JSON.stringify(payload),
            headers: {"Content-Type": "application/json"}
        });
    }

    static async logout() {
        return request("/api/auth/logout", {method: "POST"});
    }

    static async me() {
        return request("/api/auth/me");
    }

    static async refresh() {
        return request("/api/auth/refresh", {method: "POST"});
    }

    static async requestVerification() {
        return request("/api/auth/request-verification", {method: "POST"});
    }

    static async verify(uuid) {
        return request(`/api/auth/verify?uuid=${uuid}`,);
    }

}