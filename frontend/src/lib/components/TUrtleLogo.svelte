<script>
    import {Heading, Span} from "flowbite-svelte";
    import {page} from "$app/state";
    import {goto} from "$app/navigation";

    const {path = "/"} = $props();
    let canRotate = $state(true);
    let clickCount = $state(0)
    let clickGoal = getRandomInt(5, 9)

    function getRandomInt(min, max) {
        min = Math.ceil(min);   // round min up
        max = Math.floor(max);  // round max down
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    function rotate(event) {
        event.preventDefault();

        if (page.url.pathname !== path) {
            goto(path, {invalidateAll: true});
            return;
        }

        if (!canRotate)
            return;

        const animation = "animate-spin";
        const element = event.currentTarget;

        element.classList.add(animation);
        setTimeout(() => {
            element.classList.remove(animation);
            canRotate = true;
        }, 1000);
        canRotate = false;
        clickCount++;

        if (clickCount >= clickGoal) {
            console.log("trigger easter egg")
            clickCount = 0;
            clickGoal = getRandomInt(5, 9)
        }
    }
</script>

<button id="export-target" class="flex flex-col items-center select-none w-max h-max" onclick={rotate}>
    <Heading class="inline-block text-7xl">
        <Span class="text-csw">CSW</Span>
    </Heading>

    <Heading class="inline-block text-2xl tracking-[.35em]">
        <Span class="text-csw">TUrtle</Span>
    </Heading>
</button>