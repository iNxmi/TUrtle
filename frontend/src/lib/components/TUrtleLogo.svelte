<script>
    import {Heading, Span} from "flowbite-svelte";
    import { page } from "$app/state";
	import { goto } from "$app/navigation";

    let canRotate = $derived(page.url.pathname === "/");
    function rotate(event) {
        event.preventDefault();

        if (!canRotate){
            goto('/',{invalidateAll: true});
            return;
        }  

        const animation = "animate-spin";
        const element = event.currentTarget;

        element.classList.add(animation);
        setTimeout(() => {
            element.classList.remove(animation);
            canRotate = true;
        }, 1000);
        canRotate = false;
    }
</script>

<button id="export-target" class="flex flex-col items-center select-none w-max h-max" onclick={rotate}>
    <Heading class="inline-block">
        <Span class="text-7xl text-csw!">
            CSW
        </Span>
    </Heading>

    <Heading class="inline-block text-2xl tracking-[.35em]">
        <Span class="text-csw!">
            TUrtle
        </Span>
    </Heading>
</button>