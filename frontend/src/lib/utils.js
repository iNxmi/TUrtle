import request from './api/api';
import {dev} from '$app/environment';
import {error, redirect} from '@sveltejs/kit';
import {hardwarePath, deviceBookingsPath, roomBookingsPath} from '$lib/backend';

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

export function convertEventToFrontend(backendEvent, creator) {
    if (dev)
        return {
            ...backendEvent,
            isAuthor: true
        };

    if (creator) {
        return {
            ...backendEvent,
            ...(backendEvent.accessibility === "WHITELIST" ? {enableWhitelist: true, openForEveryone: false} :
                backendEvent.accessibility === "UNLOCKED" ? {openToEveryone: true, enableWhitelist: false} :
                    {enableWhitelist: false, openForEveryone: false}),
            ...(creator.id === backendEvent.creator || creator.roles.includes(4) ? {
                editable: true,
                color: '#FF6A00',
                isAuthor: true
            } : {editable: false, color: '#89ABE4', isAuthor: false}),
            accessibility: undefined
        };
    } else {
        return {
            ...backendEvent,
            ...(backendEvent.accessibility === "WHITELIST" ? {enableWhitelist: true, openForEveryone: false} :
                backendEvent.accessibility === "UNLOCKED" ? {openToEveryone: true, enableWhitelist: false} :
                    {enableWhitelist: false, openForEveryone: false}),
            accessibility: undefined
        };
    }
}

export async function fetchRoomBookings(info) {
    const url = roomBookingsPath + `?rsql=start>=${encodeURIComponent(info.startStr)};end<=${encodeURIComponent(info.endStr)}`
    const response = await request(url);

    if (!response.ok)
        return false;

    return await response.json();
}

export async function fetchDeviceBookings(info) {
    const url = deviceBookingsPath + `?rsql=start>=${encodeURIComponent(info.startStr)};end<=${encodeURIComponent(info.endStr)}`;
    const response = await request(url);
    if (!response.ok) {
        return false;
    }
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
        return redirect(307, redirectURL ? `/auth/login?redirectTo=${redirectURL}` : '/auth/login?redirectTo=/user/dashboard');

    return error(response.status, response.statusText);
}

export async function openLocker(locker, reservation) {
    //Todo
    if (reservation) {
        return reservation.status === "COLLECTION_READY" ? "DEVICE_COLLECTED" : "DEVICE_RETURNED";
    }

    return await fetch(`/api${hardwarePath}/locker?id=${locker}`);
}

export async function openDoor() {
    const response = await request(hardwarePath + `/door?seconds=3`);
    if (response.ok) {
        return true;
    }
}
export function between(number, min, max){
    return min <= number && number < max
}