import SimpleDB from './SimpleDB';
import {UserDatabase} from './users/UserDatabase';
import {EventDatabase} from './events/EventDatabase';
import {seedEvents} from './events/sampleEvents';
import {DevicebookingDatabase} from './devicebookings/DevicebookingDatabase';
import {seedDevicebookings} from './devicebookings/SampleDevicebookings';
import {ItemDatabase} from './items/ItemDatabase';
import {seedItems} from './items/SampleItems';

const sharedDB = new SimpleDB();

export const userDatabase = new UserDatabase(sharedDB);
userDatabase.seedDatabase();

export const eventDatabase = new EventDatabase(sharedDB);
seedEvents(eventDatabase);

export const devicebookingDatabase = new DevicebookingDatabase(sharedDB);
seedDevicebookings(devicebookingDatabase);

export const itemDatabase = new ItemDatabase(sharedDB);
seedItems(itemDatabase);
