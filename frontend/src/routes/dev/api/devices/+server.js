import {json} from '@sveltejs/kit';
export async function GET(){

    const devices  = [{  
        name:"iPad", category: "1", id: "0"
    },{  
        name:"Dell Laptop", category: "0", id: "1"
    },{  
        name:"Asus Laptop", category: "0", id: "2"
    },{  
        name:"Samsung Tab A", category: "1", id: "3"
    },{  
        name:"Acer Laptop", category: "0", id: "4"
    },
    ];

    return json(devices);
}