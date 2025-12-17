import request from './api/api';
import { dev } from '$app/environment';
export function convertEventToBackend(calendarEvent) {
	if (dev) {
		return calendarEvent;
	}
	return {
		title: calendarEvent.title,
		start: calendarEvent.start,
		end: calendarEvent.end,
		creator: calendarEvent.extendedProps.creator.id,
		description: calendarEvent.extendedProps.description,
		accessibility: calendarEvent.extendedProps.enableWhitelist ? "WHITELIST" : calendarEvent.extendedProps.openToEveryone ? "UNLOCKED" : "LOCKED",
		whitelist: calendarEvent.extendedProps.whitelist
	};
}
export function convertEventToFrontend(backendEvent) {
	if (dev) {
		return backendEvent;
	}
	return {
		...backendEvent,
		...(backendEvent.accessibility === "WHITELIST" ? {enableWhitelist: true, openForEveryone: false} : 
			backendEvent.accessibility === "UNLOCKED" ? {openToEveryone: true, enableWhitelist: false} : 
			{enableWhitelist: false, openForEveryone: false}),
			accessibility: undefined
	};
}
export async function fetchRoomBookings(info) {
	const response = await request(
		`/roombookings/overlapping?start=${encodeURIComponent(info.startStr)}&end=${encodeURIComponent(info.endStr)}`,
		{
			method: 'GET',
			headers: { 'Content-Type': 'application/json' }
		}
	);

	if (!response.ok) {
		return false;
	}
	const events = await response.json();
	return events;
}
