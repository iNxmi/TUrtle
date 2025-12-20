import {json} from '@sveltejs/kit';

export async function GET() {
    const payload = [
        {
            "id": 1,
            "name": "url test",
            "title": "url test",
            "content": "[this is the url we are testing](https://youtube.com)"
        },
        {
            "id": 2,
            "name": "url test sdfgsfd",
            "title": "url sdfgsfd test",
            "content": "[this is the url we are testing](https://youtube.com)"
        },
        {
            "id": 3,
            "name": "url test 22wda",
            "title": "url test22wda",
            "content": "[this is the url we are testing](https://youtube.com)"
        },
        {
            "id": 4,
            "name": "url test IZFSDFFOZUIA",
            "title": "url test IZFSDFFOZUIA",
            "content": "[this is the url we are testing](https://youtube.com)"
        }
    ]

    return json(payload);
}