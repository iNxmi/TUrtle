import SimpleDB from './SimpleDB';
import { UserDatabase } from './users/UserDatabase';
import { EventDatabase } from './events/EventDatabase';
const sharedDB = new SimpleDB();

export const userDatabase = new UserDatabase(sharedDB);
userDatabase.seedDatabase();
export const eventDatabase = new EventDatabase(sharedDB);
eventDatabase.seedDatabase();
