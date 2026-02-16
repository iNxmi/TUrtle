<script>
    import {page} from '$app/state';
    import {Heading} from 'flowbite-svelte';
    import {A404NotFoundShoppingDark, A404NotFoundSmileyDark} from "flowbite-svelte-illustrations";

    const random = Math.random()
</script>

<div class="flex flex-col justify-center bg-background min-h-svh">
    <div class="flex justify-center">
        <div class="flex flex-col gap-10">
            <Heading tag="h1" class="text-center">{page.status} {page.error.message}</Heading>

            {#if page.status === 404}
                {#if random <= 0.5}
                    <A404NotFoundShoppingDark/>
                {:else}
                    <A404NotFoundSmileyDark/>
                {/if}
            {/if}
        </div>
    </div>
</div>