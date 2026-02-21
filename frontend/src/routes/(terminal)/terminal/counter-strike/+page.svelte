<script>
    import {onMount, tick} from 'svelte';
    import {Button, Heading} from 'flowbite-svelte';
    import request from "$lib/api/api.js"

    const ITEM_WIDTH = 120;
    const VIEWPORT_WIDTH = 360;

    const initialSeconds = 3.0
    let seconds = initialSeconds;
    let items = [{
        name: '-0.5s', rarity: 'gray', weight: 13, function: () => {
            seconds -= 0.5
        }
    }, {
        name: '+0.5s', rarity: 'blue', weight: 8, function: () => {
            seconds += 0.5
        }
    }, {
        name: '+1.0s', rarity: 'purple', weight: 4, function: () => {
            seconds += 1.0
        }
    }, {
        name: '+2.0s', rarity: 'pink', weight: 4, function: () => {
            seconds += 2.0
        }
    }, {
        name: '+5.0s', rarity: 'red', weight: 4, function: () => {
            seconds += 5.0
        }
    }, {
        name: 'Success!', rarity: 'gold', weight: 4, function: () => {
            openDoor();
            seconds = initialSeconds
        }
    }];

    let reel = [];
    let offset = 0;
    let spinning = false;
    let animateClass = '';

    onMount(() => {
        reel = Array.from({length: 10}, () => weightedRandom(items));
    });

    async function openDoor() {
        const payload = {
            emojis: "💩💩💩💩💩"
        }

        const response = await request('/hardware/door/emojis', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        const json = await response.json()

        alert(JSON.stringify(json));
    }

    function weightedRandom(items) {
        const totalWeight = items.reduce((sum, item) => sum + item.weight, 0);
        let rand = Math.random() * totalWeight;
        for (const item of items) {
            if (rand < item.weight) return item;
            rand -= item.weight;
        }
        return items[items.length - 1];
    }

    async function openCase() {
        if (spinning) return;
        spinning = true;

        reel = Array.from({length: 50}, () => weightedRandom(items));

        animateClass = '';
        offset = 0;
        await tick();
        void document.querySelector('.reel').offsetHeight;

        animateClass = 'animate';

        const targetIndex = 25;
        offset = -(targetIndex * ITEM_WIDTH) + (VIEWPORT_WIDTH / 2) - (ITEM_WIDTH / 2);

        setTimeout(() => {
            spinning = false;
            const centerIndex = Math.round((-offset + VIEWPORT_WIDTH / 2) / ITEM_WIDTH);
            const selectedItem = reel[centerIndex];
            if (selectedItem?.function) selectedItem.function();
        }, 5000);
    }
</script>

<div class="flex flex-col justify-center h-full">
    <div class="case-container">
        <Heading tag="h2">
            Duration: {seconds}s
        </Heading>

        <div class="viewport">
            <!-- Normal reel -->
            <div class="reel {animateClass}" style="transform: translateX({offset}px);">
                {#each reel as item}
                    <div class="item {item.rarity}">{item.name}</div>
                {/each}
            </div>

            <!-- Blur overlay outside the lens -->
            <div class="blur-overlay"></div>

            <div class="marker"></div>
        </div>

        <Button onclick={openCase} disabled={spinning}>Open Door</Button>
    </div>
</div>

<style>
    .case-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
    }

    .viewport {
        width: 550px;
        height: 120px;
        overflow: hidden;
        position: relative;
        border: 4px solid #333;
    }

    .reel {
        display: flex;
        transform: translateX(0);
        position: relative;
        z-index: 1;
    }

    .reel.animate {
        transition: transform 5s cubic-bezier(0.1, 0.8, 0.2, 1);
    }

    .item {
        min-width: 120px;
        height: 120px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        color: white;
        box-sizing: border-box;
        border-right: 2px solid #000;
    }

    .gray {
        background: gray;
    }

    .blue {
        background: #4b69ff;
    }

    .purple {
        background: #8847ff;
    }

    .pink {
        background: #d32ce6;
    }

    .red {
        background: #eb4b4b;
    }

    .gold {
        background: #e4ae39;
    }

    /* Magnifying lens */
    .lens {
        position: absolute;
        top: 50%;
        left: 50%;
        width: 180px; /* diameter of lens */
        height: 180px;
        border-radius: 50%;
        overflow: hidden;
        transform: translate(-50%, -50%);
        z-index: 3;
        border: 0px solid gray;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
    }

    .lens .reel {
        transform-origin: center center;
    }

    /* Blur overlay outside lens */
    .blur-overlay {
        position: absolute;
        inset: 0;
        z-index: 2;
        pointer-events: none;
        backdrop-filter: blur(4px);
        -webkit-backdrop-filter: blur(4px);

        /* cut a circular hole in the center for the lens */
        mask-image: radial-gradient(circle 90px at center, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0) 90px, rgba(0, 0, 0, 1) 91px, rgba(0, 0, 0, 1) 100%);
        -webkit-mask-image: radial-gradient(circle 90px at center, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0) 90px, rgba(0, 0, 0, 1) 91px, rgba(0, 0, 0, 1) 100%);
    }

    .marker {
        position: absolute;
        top: 0;
        bottom: 0;
        left: 50%;
        width: 4px;
        background: #ffd700;
        transform: translateX(-50%);
        pointer-events: none;
        z-index: 4;
    }

    button {
        padding: 0.75rem 1.5rem;
        font-size: 1rem;
        cursor: pointer;
    }
</style>