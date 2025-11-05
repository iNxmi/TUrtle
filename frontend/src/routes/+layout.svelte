<script>
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import { m } from '$lib/paraglide/messages.js';
	import {
		ChartOutline,
		GridSolid,
		MailBoxSolid,
		UserSolid,
		UsersGroupSolid,
		DesktopPcSolid,
		UserHeadsetSolid,
		CalendarMonthSolid,
		CalendarEditSolid,
		UserSettingsSolid,
		NewspaperSolid
	} from 'flowbite-svelte-icons';
	import { page } from '$app/state';
	import {
		Sidebar,
		SidebarGroup,
		SidebarItem,
		SidebarButton,
		uiHelpers,
		ThemeProvider,
		sidebar
	} from 'flowbite-svelte';

	let activeUrl = $state(page.url.pathname);
	const demoSidebarUi = uiHelpers();
	let isDemoOpen = $state(false);
	const closeDemoSidebar = demoSidebarUi.close;
	$effect(() => {
		isDemoOpen = demoSidebarUi.isOpen;
		activeUrl = page.url.pathname;
	});
	const theme = {
		sidebar: {
			base: 'bg-secondary'
		}
	};
	const spanClass = 'flex-1 ms-3 whitespace-nowrap';
	const activeClass =
		'flex items-center p-2 text-base font-normal text-white bg-primary-600 dark:bg-primary-700 rounded-lg dark:text-white hover:bg-primary-800 dark:hover:bg-primary-800';
	const nonActiveClass =
		'flex items-center p-2 text-base font-normal text-green-900 rounded-lg dark:text-white hover:bg-green-100 dark:hover:bg-green-700';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
</svelte:head>

<div class="min-h-screen">
	<SidebarButton onclick={demoSidebarUi.toggle} class="mb-2" />

	<ThemeProvider {theme}>
		<Sidebar
			alwaysOpen
			{activeUrl}
			backdrop={false}
			isOpen={isDemoOpen}
			closeSidebar={closeDemoSidebar}
			params={{ x: -50, duration: 50 }}
			classes={{ nonactive: nonActiveClass, active: activeClass }}
			position="absolute"
			class="h-full border-r border-table-dark"
		>
			<SidebarGroup>
				<SidebarItem label="Dashboard" href="/">
					{#snippet icon()}
						<ChartOutline
							class="h-5 w-5 text-gray-500 transition duration-75 group-hover:text-gray-900 dark:text-gray-400 dark:group-hover:text-white"
						/>
					{/snippet}
				</SidebarItem>
				<SidebarItem label={m.profile()} href="/profile">
					{#snippet icon()}
						<UserSolid class="text-green-700 h-5 w-5 transition duration-75 " />
					{/snippet}
				</SidebarItem>
				<SidebarItem label={m.reservations()} href="/reservation">
					{#snippet icon()}
						<DesktopPcSolid class="text-green-700 h-5 w-5 transition duration-75 " />
					{/snippet}
				</SidebarItem>
				<SidebarItem label={m.bookings()} href="/bookings">
					{#snippet icon()}
						<CalendarMonthSolid class="text-green-700 h-5 w-5 transition duration-75" />
					{/snippet}
				</SidebarItem>
				<SidebarItem label={m.support()} href="/support">
					{#snippet icon()}
						<UserHeadsetSolid class="text-green-700 h-5 w-5 transition duration-75" />
					{/snippet}
				</SidebarItem>
			</SidebarGroup>
			<SidebarGroup border borderClass="pt-2 mt-4 border-t border-primary dark:border-gray-700">
				<span class="text-green-800 ml-1">Admin</span>
				<div class="space-y-2">
					<SidebarItem label={m.manage_users()} {spanClass} href="/admin/users">
						{#snippet icon()}
							<UsersGroupSolid class="text-green-700 h-5 w-5 transition duration-75" />
						{/snippet}
					</SidebarItem>
					<SidebarItem label={m.manage_bookings()} href="/admin/bookings">
						{#snippet icon()}
							<CalendarEditSolid class="text-green-700 h-5 w-5 transition duration-75" />
						{/snippet}
					</SidebarItem>
					<SidebarItem label={m.manage_support_tickets()} href="/admin/support">
						{#snippet icon()}
							<UserHeadsetSolid class="text-green-700 h-5 w-5 transition duration-75" />
						{/snippet}
					</SidebarItem>
					<SidebarItem label={m.manage_news()} href="/admin/news">
						{#snippet icon()}
							<NewspaperSolid class="text-green-700 h-5 w-5 transition duration-75" />
						{/snippet}
					</SidebarItem>
					<SidebarItem label={m.admin_settings()} href="/admin/settings">
						{#snippet icon()}
							<UserSettingsSolid class="text-green-700 h-5 w-5 transition duration-75" />
						{/snippet}
					</SidebarItem>
				</div>
			</SidebarGroup>
		</Sidebar>
	</ThemeProvider>
	<main class="overflow-auto px-4 md:ml-64 bg-background-50 min-h-screen">
		<div class="container mx-auto max-w-12xl mb-12 md:pt-20">
			{@render children?.()}
		</div>
	</main>
</div>
