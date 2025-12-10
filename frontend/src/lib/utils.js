import request from './api/api';
import { dev } from '$app/environment';
export function convertEventToBackend(calendarEvent) {
	return {
		title: calendarEvent.title,
		start: calendarEvent.start,
		end: calendarEvent.end,
		creator: calendarEvent.extendedProps.creator.id,
		description: calendarEvent.extendedProps.description,
		...(calendarEvent.extendedProps.whitelist && {
			whitelist: calendarEvent.extendedProps.whitelist
		})
	};
}
export function convertEventToFrontend(backendEvent) {
	if (dev) {
		return backendEvent;
	}
	return {
		...backendEvent
		/* start: backendEvent.startTime,
			end: backendEvent.endTime,
			startTime: undefined,
			endTime: undefined */
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
