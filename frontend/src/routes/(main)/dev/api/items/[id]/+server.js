import { json } from '@sveltejs/kit';

export async function GET({params}){
    const devices  = [{  
        name:"iPad", category: "1", id: "0", locker: "3"
    },{  
        name:"Dell Laptop", category: "1", id: "1", locker: "5"
    },{  
        name:"Asus Laptop", category: "5", id: "2", locker: "6"
    },{  
        name:"Samsung Tab A", category: "2", id: "3", locker: "4"
    },{  
        name:"Acer Laptop", category: "1", id: "4", locker: "5"
    },
    ];

    return json(devices.find((device) => device.id === params.id));
}