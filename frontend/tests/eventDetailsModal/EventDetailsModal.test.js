import { expect, test } from 'vitest'
import { render } from 'vitest-browser-svelte'
import EventDetailsModal from '../../src/lib/components/EventDetailsModal.svelte'
import { events } from '../../src/lib/server/events/sampleEvents'
import { users } from '../../src/lib/server/users/sampleUsers'

const selectedEvent = events[0];
const creator = users[events[0].extendedProps.creator];

selectedEvent.start = new Date(selectedEvent.start);
selectedEvent.end = new Date(selectedEvent.end);

const durationInNanos = Math.abs(selectedEvent.end.getTime() - selectedEvent.start.getTime());
const durationInHours = Math.floor( durationInNanos / (1000 * 60 * 60)); 
const duration = Math.floor(durationInNanos / (1000 * 60)) - durationInHours * 60;

const durationInMinutes = duration < 10 ? '0'+duration : duration;

test('displays event title on modal', async () => {
   
    const screen = render(EventDetailsModal, { showEventDetailsModal:true, selectedEvent:selectedEvent, creator:creator});
    await expect.element(screen.getByText(selectedEvent.title)).toBeInTheDocument();
}); 

test('displays event start date on modal', async () => {
   
    const screen = render(EventDetailsModal, { showEventDetailsModal:true, selectedEvent:selectedEvent, creator:creator});
    await expect.element(screen.getByText(selectedEvent.start.toLocaleDateString())).toBeInTheDocument();
}); 
test('displays event start time on modal', async () => {
   
    const screen = render(EventDetailsModal, { showEventDetailsModal:true, selectedEvent:selectedEvent, creator:creator});
    await expect.element(screen.getByText(selectedEvent.start.toLocaleTimeString().substring(0,5))).toBeInTheDocument();
}); 
test('displays event duration on modal', async () => {
   
    const screen = render(EventDetailsModal, { showEventDetailsModal:true, selectedEvent:selectedEvent, creator:creator});
    await expect.element(screen.getByText(`${durationInHours}:${durationInMinutes}`)).toBeInTheDocument();
});
test('displays event description on modal', async () => {
   
    const screen = render(EventDetailsModal, { showEventDetailsModal:true, selectedEvent:selectedEvent, creator:creator});
    await expect.element(screen.getByText(selectedEvent.extendedProps.description)).toBeInTheDocument();
});
test('displays event creator name on modal', async () => {
   
    const screen = render(EventDetailsModal, { showEventDetailsModal:true, selectedEvent:selectedEvent, creator:creator});
    await expect.element(screen.getByText(creator.firstName+" "+ creator.lastName)).toBeInTheDocument();
});


