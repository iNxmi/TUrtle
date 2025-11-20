import {dev} from '$app/environment';

export default async function request(url, options) {
    const prefix = dev ? "dev/api" : "api";
    const endpoint = `/${prefix}${url}`;
    return fetch(endpoint, options);
}