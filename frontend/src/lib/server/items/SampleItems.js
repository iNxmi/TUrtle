export const seedItems = (db) => {
 
    const items  = [{  
        name:"Dell Laptop", category: "0", locker: "5", needsConfirmation: true 
    },{  
        name:"Asus Laptop", category: "0", locker: "6", needsConfirmation: true
    },{  
        name:"iPad", category: "1",  locker: "3", needsConfirmation: false
    },{  
        name:"Samsung Tab A", category: "1", locker: "4", needsConfirmation: false 
    },{
        name:"Acer Laptop", category: "0", locker: "5", needsConfirmation: true
    },
    ];

    items.forEach((item) => db.createItem(item));
}
