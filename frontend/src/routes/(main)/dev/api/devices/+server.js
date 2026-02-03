import {json} from '@sveltejs/kit';
export async function GET(){

    const devices  = [{  
        name:"iPad", category: "1", id: "0", locker: "3"
    },{  
        name:"Dell Laptop", category: "0", id: "1", locker: "5"
    },{  
        name:"Asus Laptop", category: "0", id: "2", locker: "6"
    },{  
        name:"Samsung Tab A", category: "1", id: "3", locker: "4"
    },{  
        name:"Acer Laptop", category: "0", id: "4", locker: "5"
    },
    ];

    return json(devices);
}