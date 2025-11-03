<svelte:head>
    <link rel="icon" href="/favicon.ico"/>
</svelte:head>

<script>
    import {onMount} from 'svelte';
    import './layout.css';

    export let data;
    const user = data.user;

    // things everyone can see
    const navItems = [
        {label: "Dashboard", href: "/dashboard"},
        {label: "Help", href: "/help"},
        {label: "Contact", href: "/contact"}
    ];

    // things only logged in users can see
    if (user) {
        navItems.push({label: "Admin Panel", href: "/admin"});
    }

    function handleLogout() {
        fetch("/api/logout", {method: "POST"}).then(() => {
            location.reload(); // refresh page to update user state
        });
    }
</script>

<main class="layout">
    <div class="sidebar">
        <div class="nav">
            {#each navItems as item}
                <a href="{item.href}">{item.label}</a>
            {/each}
        </div>

        <div class="bottom">
            <div class="status">
                {#if user}
                    👤 Angemeldet als {user.username}
                {:else}
                    🚪 Nicht angemeldet
                {/if}
            </div>

            {#if user}
                <button on:click={handleLogout}>Logout</button>
            {:else}
                <a href="/login">
                    <button>Login</button>
                </a>
            {/if}
        </div>
    </div>

    <div class="main">
        <slot/>
    </div>
</main>