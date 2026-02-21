import {json} from '@sveltejs/kit';

export async function GET({params}) {

    const categories = [
        {
            id: "1",
            name: "Beamer",
            createdAt: "2026-01-22T13:29:12.042594Z"
        },
        {
            id: "2",
            name: "Laptop",
            createdAt: "2026-01-22T13:29:12.043751Z"
        },
        {
            id: "3",
            name: "Tablet",
            createdAt: "2026-01-22T13:29:12.044393Z"
        },
        {
            id: "4",
            name: "Lernmedium",
            createdAt: "2026-01-22T13:29:12.045038Z"
        },
        {
            id: "5",
            name: "Gaming",
            createdAt: "2026-01-22T13:29:12.046579Z"
        },
        {
            id: "6",
            name: "Streaming",
            createdAt: "2026-01-22T13:29:12.047706Z"
        },
        {
            id: "7",
            name: "Zubehör",
            createdAt: "2026-01-22T13:29:12.049493Z"
        }
    ];

    return json(categories.find((category) => category.id === params.id));
}