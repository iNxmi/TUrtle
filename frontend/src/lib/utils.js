export function convertEventToBackend(calendarEvent){
    return {
        title: calendarEvent.title,
        startTime: calendarEvent.start,
        endTime: calendarEvent.end,
        creator: calendarEvent.extendedProps.creator.id,
        description: calendarEvent.extendedProps.description
    }
}
export function convertEventToFrontend(backendEvent){
    return {
        ...backendEvent,
        start: backendEvent.startTime,
        end: backendEvent.endTime,
        startTime: undefined,
        endTime: undefined
    }
}