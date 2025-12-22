import request from './api/api';
import {dev} from '$app/environment';
import {error, redirect} from '@sveltejs/kit';

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
    if (dev)
        return backendEvent;

    return {
        ...backendEvent,
        ...(backendEvent.accessibility === "WHITELIST" ? {enableWhitelist: true, openForEveryone: false} :
            backendEvent.accessibility === "UNLOCKED" ? {openToEveryone: true, enableWhitelist: false} :
                {enableWhitelist: false, openForEveryone: false}),
        accessibility: undefined
    };
}

export async function fetchRoomBookings(info) {
    const url = `/roombookings/overlapping?start=${encodeURIComponent(info.startStr)}&end=${encodeURIComponent(info.endStr)}`
    const response = await request(url);

    if (!response.ok)
        return false;

    return await response.json();
}

/**
 * Checks authorization and errors
 * @param {Response} response - the fetch response to check
 * @param {string} redirectURL - the pathname of this page e.g. url.pathname
 * @returns
 */
export function checkAuthorization(response, redirectURL) {
    if (response.ok)
        return;

    if (response.status === 401)
        return redirect(307, `/auth/login?redirectTo=${redirectURL}`);

    return error(response.status, response.statusText);
}
