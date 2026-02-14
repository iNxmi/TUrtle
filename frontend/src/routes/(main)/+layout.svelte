<script>
    import Navigation from "$lib/components/Navigation.svelte";
    import Footer from "$lib/components/Footer.svelte";
    import ConfirmProvider from '$lib/components/ConfirmProvider.svelte';

    let {data, children} = $props();
    let permissions = $derived(data.permissions);
    let user = $derived(data.user);
</script>

<div class="flex">
    <Navigation user={user} permissions={permissions}/>

    <div class="min-h-svh justify-between flex flex-col w-full bg-background">
        <div class="m-5">
            <ConfirmProvider>
                {@render children?.()}
            </ConfirmProvider>
        </div>

        <Footer/>
    </div>
</div>