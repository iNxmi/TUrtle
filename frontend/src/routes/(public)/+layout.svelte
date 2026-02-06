<script>
    import { goto } from '$app/navigation';
    import {Heading, Span, Button} from 'flowbite-svelte';
    import Footer from '$lib/components/Footer.svelte';
    import { onMount } from 'svelte';
    import { SunOutline, MoonOutline } from 'flowbite-svelte-icons';
    let { data, children } = $props();
    
    let loggedIn = $derived(data.loggedIn);
    let darkmode = $state(false);
    onMount(() => {
        darkmode = localStorage ? localStorage.getItem('darkmode') : false;
    });
    
    function toggleDarkMode() {
        darkmode = !darkmode;
        localStorage.setItem('darkmode', darkmode? "true": "");
        document.documentElement.classList.remove(darkmode ? 'light' : 'dark');
        document.documentElement.classList.add(darkmode ? 'dark' : 'light');
        
        /* document.documentElement.data */
    }
</script>

<div class="min-h-svh w-full flex flex-col justify-between bg-background">
    <div> 
        <div class='flex justify-end m-5'>
            <div class="flex flex-row items-center gap-2">
                <button onclick={toggleDarkMode} class="cursor-pointer inline-flex items-center justify-center text-md dark:text-white ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-0 disabled:pointer-events-none disabled:opacity-50 hover:bg-gray-50 dark:hover:bg-gray-800 rounded-md px-3 h-8">
                    {#if darkmode}
                    <MoonOutline class="text-white mr-1" /> _Dunkel_
                    {:else}
                    <SunOutline class="mr-1"/> _Hell_
                    {/if}
                </button>
                {#if loggedIn}
                <Button class="cursor-pointer" onclick={() => goto('/dashboard')}>_Go to Dashboard_</Button>
                {:else}
                <Button class="cursor-pointer" onclick={() => goto('/auth/login')}>_Login_</Button>
                {/if}
                <Button class="cursor-pointer" onclick={() => goto('/auth/register')}>_Register_</Button>
            </div>
        </div>
        <Heading tag="h3" class="text-center text-text!">
            Welcome to <Span class="text-csw">TUrtle</Span>
        </Heading>
    </div>
    
    <div class="w-full p-10 bg-background">
        {@render children?.()}
    </div>
<Footer />
</div>