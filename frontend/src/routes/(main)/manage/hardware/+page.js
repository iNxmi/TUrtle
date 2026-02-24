import {Lockers} from "$lib/api";

export async function load({url}) {
    const response = await Lockers.getCollection();
    const lockers = await response.json();

    return {lockers: lockers}
}