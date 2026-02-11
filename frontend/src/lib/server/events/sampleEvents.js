const getEventTimes = (daysOffset, hour, duration = 1) => {
	const start = new Date();

	start.setDate(start.getDate() + daysOffset);
	start.setHours(hour);

	const end = new Date(start);
	end.setHours(start.getHours() + duration);

	return {
		start: start.toISOString(),
		end: end.toISOString()
	}
}
export const seedEvents = (db) => {


 const events = [
	{
		id: "0",
		title: 'Team Daily Standup',
		...getEventTimes(-7, 7, 2),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "1",
		title: 'Project Alpha Kickoff',
		...getEventTimes(-6, 12, 1),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "2",
		title: 'Client Discovery Call',
		...getEventTimes(-4, 17, 4),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "3",
		title: 'Code Review: Backend Service',
		...getEventTimes(-3, 6, 2),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "4",
		title: 'UI/UX Feedback Meeting',
		...getEventTimes(-3, 10, 1),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "5",
		title: 'Hiring Interview - Candidate 1',
		...getEventTimes(-2, 13, 4),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "6",
		title: 'Q3 Budget Review',
		...getEventTimes(-2, 18, 2),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "7",
		title: 'Infrastructure Check',
		...getEventTimes(-1, 8, 3),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: [],
			status: 'REQUESTED'
		}
	},
	{
		id: "8",
		title: 'Deep Work Slot (No Interruptions)',
		...getEventTimes(-1, 14, 2),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "9",
		title: 'Sprint Planning Session',
		...getEventTimes(0, 9, 2),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "10",
		title: '1:1 with Senior Lead',
		...getEventTimes(0, 12, 1),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "11",
		title: 'Documentation Workshop',
		...getEventTimes(1, 6, 4),
		extendedProps: {
			creator: '1',
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "12",
		title: 'Investor Update Prep',
		...getEventTimes(2, 10, 3),
		extendedProps: {
			creator: "1",
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "13",
		title: 'Marketing Strategy Sync',
		...getEventTimes(3, 11, 2),
		extendedProps: {
			creator: "1",
			description: 'Test Description',
			enableWhitelist: false,
			whitelist: []
		}
	},
	{
		id: "14",
		title: 'QA Testing Huddle',
		...getEventTimes(4, 8, 1),
		extendedProps: {
			creator: "1",
			description: 'Test Description',
			enableWhitelist: true,
			whitelist: []
		}
	}
];

events.forEach( (event) => db.createEvent(event));
}