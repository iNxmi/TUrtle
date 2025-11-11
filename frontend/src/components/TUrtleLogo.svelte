<script>
    import {Heading, Span} from "flowbite-svelte";

    let canRotate = $state(true);

    function rotate(event) {
        event.preventDefault();

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
    }
</script>

<button class="flex flex-col select-none" onclick={rotate}>
    <Heading class="text-center">
        <Span class="text-7xl" gradient="redToYellow">CSW</Span>
    </Heading>
    <Heading class="text-center text-2xl tracking-[.35em]">
        <Span class="text-orange-400">TUrtle</Span>
    </Heading>
</button>