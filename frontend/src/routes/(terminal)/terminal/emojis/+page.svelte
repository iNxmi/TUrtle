<script>
    import request from "$lib/api/api.js";

    import {Hr} from "flowbite-svelte";

    const CODE_LENGTH = 5;

    let {data} = $props();
    let emojis = $derived(data.emojis);

    let code = $state([]);

    function getGrid() {
        const randomized = getRandomized(emojis)

        let grid = [[], [], [], [], [], []];

        for (let y = 0; y < 6; y++) {
            for (let x = 0; x < 6; x++) {
                const index = x + y * 6;
                grid[x][y] = randomized[index];
            }
        }

        return grid;
    }

    function shuffle() {
        emoji_rows = getGrid()
    }

    let emoji_rows = $state(getGrid())

    function addEmoji(emoji) {
        if (code.length >= CODE_LENGTH)
            return

        code[code.length] = emoji;

        if (code.length === CODE_LENGTH)
            submitInput();
    }

    let success = $state(null)
    let blink = $state(true)

    async function submitInput() {
        shuffle();

        const payload = {
            emojis: code.join("")
        };

        const response = await request('/hardware/door/emojis', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        success = response.ok

        const startTime = Date.now()
        const blinkInterval = 500
        const duration = 3000
        const interval = setInterval(() => {
            const elapsedTime = Date.now() - startTime;

            blink = Math.floor(elapsedTime / blinkInterval) % 2 === 0;

            if (elapsedTime > duration) {
                clearInterval(interval);
                code = [];
                success = null;
                blink = true;
            }
        }, 50)
    }

    const removeEmoji = () => {
        if (code.length <= 0 || code.length >= CODE_LENGTH)
            return;

        code.pop();
    }

    function getRandomized(array) {
        let copy = array.slice();
        for (let i = 0; i < copy.length; i++) {
            let j = parseInt((Math.random() * (copy.length - i))) + i;

            let tmp = copy[i];
            copy[i] = copy[j];
            copy[j] = tmp;
        }

        return copy;
    }

    shuffle();

    let defaultModal = $state(false);

    const buttonClasses = "flex-1 p-2 select-none rounded-2xl dark:bg-background-secondary/30 text-[2em] not-active:shadow-sm/30 active:inset-shadow-sm/30 disabled:shadow-none disabled:inset-shadow-sm/30";
</script>

<div class="flex flex-col gap-5 h-full">
    <div class="flex gap-[2vw]">
        <!--                Green Circle    🟢-->
        <!--                Red Circle      🔴-->
        <!--                Blue Circle     🔵-->
        <!--                Orange Circle   🟠-->
        <!--                Yellow Circle   🟡-->
        {#each Array.from({length: CODE_LENGTH}) as _, index}
            <button disabled={index < code.length} class={buttonClasses}>
                {#if success === null}
                     {#if index < code.length}
                         🟡
                     {:else}
                         🔵
                     {/if}
                {:else}
                    {#if blink === true}
                        {#if success === true}
                            🟢
                        {:else if success === false}
                            🔴
                        {/if}
                    {:else}
                        🔵
                    {/if}
                {/if}
            </button>
        {/each}

        <button disabled={code.length <= 0 || code.length >= CODE_LENGTH}
                type="button"
                class={buttonClasses}
                onclick={removeEmoji}>
            🔙
        </button>
    </div>

    <Hr class="m-0 p-0"/>

    <div id="keyboard" class="flex-1 flex flex-col gap-[2vh]">
        {#each emoji_rows as row}
            <div class="flex-1 flex gap-[2vw]">
                {#each row as emoji}
                    <button disabled={code.length >= CODE_LENGTH}
                            type="button"
                            class={buttonClasses}
                            onclick={() => addEmoji(emoji)}>
                        {emoji}
                    </button>
                {/each}
            </div>
        {/each}
    </div>
</div>