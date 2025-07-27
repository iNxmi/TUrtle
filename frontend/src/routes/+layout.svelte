<script>
	let { children } = $props();
</script>

<svelte:head>
    <link rel="icon" href="/favicon.ico" />
</svelte:head>

<nav>
	<a href="/">Home</a>
	<a href="/about">About</a>
	<a href="/settings">Settings</a>
</nav>

{@render children()}