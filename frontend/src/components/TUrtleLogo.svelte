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

    import {toSvg} from "html-to-image";

    async function exportAsSVG() {
        alert("clicked");

        const node = document.getElementById('export-target');
        const dataUrl = await toSvg(node);

        // Download SVG
        const link = document.createElement('a');
        link.download = 'component.svg';
        link.href = dataUrl;
        link.click();
    }
</script>

<button id="export-target" class="border select-none flex flex-col items-center p-2 w-max h-max" onclick={rotate}>

    <Heading class="inline-block">
        <Span class="text-7xl" gradient="redToYellow">
            CSW
        </Span>
    </Heading>

    <Heading class="inline-block text-2xl tracking-[.35em]">
        <Span class="text-orange-400">
            TUrtle
        </Span>
    </Heading>

</button>

<button onclick={exportAsSVG} class="px-3 py-1 bg-gray-700 text-white rounded">Export SVG</button>