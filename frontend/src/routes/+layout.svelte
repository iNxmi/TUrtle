<script>
	import '../app.css';
	import { onMount } from 'svelte';
	import favicon from '$lib/assets/favicon.svg';

	import { m } from '$lib/paraglide/messages.js';

	import { setLocale } from '$lib/paraglide/runtime.js';
	let language = $state('en');

	function updateLanguage(event) {
		event.preventDefault();

		const select = document.getElementById('language');
		language = select.options[select.selectedIndex].value;

		setLocale(language);
	}

	import {
		UserSolid,
		UsersGroupSolid,
		DesktopPcSolid,
		UserHeadsetSolid,
		CalendarMonthSolid,
		CalendarEditSolid,
		UserSettingsSolid,
		NewspaperSolid,
		SunSolid,
		MoonSolid
	} from 'flowbite-svelte-icons';
	import { page } from '$app/state';
	import {
		Sidebar,
		SidebarGroup,
		SidebarItem,
		SidebarButton,
		uiHelpers,
		ThemeProvider,
		sidebar,
		divider
	} from 'flowbite-svelte';

	let activeUrl = $state(page.url.pathname);
	const demoSidebarUi = uiHelpers();
	let isDemoOpen = $state(false);
	const closeDemoSidebar = demoSidebarUi.close;
	let darkmode = $state(false);

	$effect(() => {
		isDemoOpen = demoSidebarUi.isOpen;
		activeUrl = page.url.pathname;
	});
	onMount(() => {
		if (document.documentElement.className === 'dark') {
			darkmode = true;
		}
	});
	const theme = {
		sidebar: {
			base: 'bg-secondary dark:bg-secondary'
		}
	};
	const spanClass = 'flex-1 ms-3 whitespace-nowrap';
	const activeClass =
		'flex items-center p-2 text-base font-normal text-white bg-primary-600 dark:bg-primary-700 rounded-lg dark:text-white hover:bg-primary-800 dark:hover:bg-primary-800';
	const nonActiveClass =
		'flex items-center p-2 text-base font-normal text-green-900 rounded-lg dark:text-white hover:bg-green-100 dark:hover:bg-green-700';

	let { children } = $props();
	function toggleDarkmode() {
		if (document.documentElement.className === 'light') {
			document.documentElement.classList.remove('light');
			document.documentElement.classList.add('dark');
			darkmode = true;
		} else {
			document.documentElement.classList.remove('dark');
			document.documentElement.classList.add('light');
			darkmode = false;
		}
	}
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
</svelte:head>

<!-- <div class="min-h-screen">
 -->
<div class="h-full flex flex-col">
	<div class="flex-1 overflow-auto">
		<div class="h-screen overflow-hidden">
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
					<div class="flex mb-3 h-30 w-57 items-center justify-center">
						<img src="csw_cropped.png" alt="CSW Icon" />
					</div>

					<SidebarGroup>
						<div class="mb-1">
							<span class="text-green-800 ml-1">{m.sidebar_category_public()}</span>
						</div>
						<SidebarItem label={m.sidebar_login()} href="/login">
							{#snippet icon()}
								<UserSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
						<SidebarItem label={m.sidebar_register()} href="/register">
							{#snippet icon()}
								<UserSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
						<SidebarItem label={m.sidebar_support()} href="/support">
							{#snippet icon()}
								<UserHeadsetSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
					</SidebarGroup>
					<SidebarGroup border borderClass="pt-2 mt-4 border-t border-primary dark:border-gray-700">
						<div class="mb-1">
							<span class="text-green-800 ml-1">{m.sidebar_category_user()}</span>
						</div>
						<SidebarItem label={m.sidebar_dashboard()} href="/">
							{#snippet icon()}
								<NewspaperSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
						<SidebarItem label={m.sidebar_profile()} href="/profile">
							{#snippet icon()}
								<UserSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
						<SidebarItem label={m.sidebar_reservations()} href="/reservation">
							{#snippet icon()}
								<DesktopPcSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
						<SidebarItem label={m.sidebar_bookings()} href="/bookings">
							{#snippet icon()}
								<CalendarMonthSolid class="text-green-700 h-5 w-5 transition duration-75" />
							{/snippet}
						</SidebarItem>
					</SidebarGroup>
					<SidebarGroup border borderClass="pt-2 mt-4 border-t border-primary dark:border-gray-700">
						<div class="mb-1">
							<span class="text-green-800 ml-1">{m.sidebar_category_admin()}</span>
						</div>
						<div class="space-y-2">
							<SidebarItem label={m.sidebar_manage_users()} {spanClass} href="/admin/users">
								{#snippet icon()}
									<UsersGroupSolid class="text-green-700 h-5 w-5 transition duration-75" />
								{/snippet}
							</SidebarItem>
							<SidebarItem label={m.sidebar_manage_bookings()} href="/admin/bookings">
								{#snippet icon()}
									<CalendarEditSolid class="text-green-700 h-5 w-5 transition duration-75" />
								{/snippet}
							</SidebarItem>
							<SidebarItem label={m.sidebar_manage_support_tickets()} href="/admin/support">
								{#snippet icon()}
									<UserHeadsetSolid class="text-green-700 h-5 w-5 transition duration-75" />
								{/snippet}
							</SidebarItem>
							<SidebarItem label={m.sidebar_manage_news()} href="/admin/news">
								{#snippet icon()}
									<NewspaperSolid class="text-green-700 h-5 w-5 transition duration-75" />
								{/snippet}
							</SidebarItem>
							<SidebarItem label={m.sidebar_admin_settings()} href="/admin/settings">
								{#snippet icon()}
									<UserSettingsSolid class="text-green-700 h-5 w-5 transition duration-75" />
								{/snippet}
							</SidebarItem>
						</div>
					</SidebarGroup>

					<div class="flex absolute inset-x-0 bottom-0 m-3">
						<select id="language" class="flex-1" onchange={updateLanguage}>
							<option value="en" selected>English</option>
							<option value="de">Deutsch</option>
							<option value="ja">日本語</option>
							<option value="ar">_arabic</option>
							<option value="ru">_russian</option>
							<option value="vi">_vietnamese</option>
						</select>
					</div>
				</Sidebar>
			</ThemeProvider>
			<div class="absolute top-0 left-0 right-0 z-50 hidden md:block">
				<div class="absolute inset-0 bg-background/86 backdrop-blur-sm">
					<!-- <div class="relative z-10 px-6 py-2"> -->
					<div class="container mx-auto max-w-full p-8">
						<div class="h-16 flex justify-end">
							<button
								onclick={toggleDarkmode}
								class="cursor-pointer inline-flex items-center justify-center whitespace-nowrap"
							>
								{#if darkmode}
									<SunSolid class="text-accent h-10 w-10 transition duration-75" />
								{:else}
									<MoonSolid class="text-accent h-10 w-10 transition duration-75" />
								{/if}
							</button>
						</div>
						<!-- </div> -->
					</div>
				</div>
			</div>
			<main class="overflow-auto px-4 md:ml-64 bg-background-50 min-h-screen">
				<div class="container mx-auto max-w-12xl mb-12 md:pt-20">
					{@render children?.()}
				</div>
			</main>
		</div>
	</div>
</div>
