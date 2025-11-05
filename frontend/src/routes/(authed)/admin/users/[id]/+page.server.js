import * as db from '$lib/server/UserDatabase.js'

export async function load({params}){

    const id = params.id;

    const user = db.getUser(id);
    return { user };

}