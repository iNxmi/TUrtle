import {json} from '@sveltejs/kit';
import {dev} from '$app/environment';

export async function POST({request, fetch}) {
    if (dev) {
        const username = 'Test';
        return json({username});
    }

    const body = await request.json();
    const response = await fetch('http://backend:8080/api/auth/login', {
        method: 'POST',
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const setCookie = response.headers.getSetCookie();

    return response
}
