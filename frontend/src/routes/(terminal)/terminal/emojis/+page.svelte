<script>
    import request from "$lib/api/api.js";

    import {Hr} from "flowbite-svelte";

    const CODE_LENGTH = 5;

    let {data} = $props();
    let emojis = $derived(data.emojis);

    let password = $state([]);

    const make_rows = arr => {
        let rows = [[], [], [], [], [], []];
        let row_number = 0;
        let index = 0;
        for (let i = 0; i < arr.length; i++) {
            rows[row_number][index] = arr[i];
            index++;
            if (index === 6) {
                index = 0;
                row_number++;
            }
        }
        return rows;
    }
    let emoji_rows = $derived(make_rows(emojis));

    function addEmoji(emoji) {
        if (password.length >= CODE_LENGTH)
            return

        password[password.length] = emoji;

        if (password.length === CODE_LENGTH)
            submitInput();
    }

    let success = $state(null)
    let blink = $state(true)

    async function submitInput() {
        const payload = {
            emojis: password.join("")
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
                shuffle();
                password = [];
                success = null;
                blink = true;
            }
        }, 50)
    }

    const removeEmoji = () => {
        if (password.length <= 0 || password.length >= CODE_LENGTH)
            return;

        password.pop();
    }

    const shuffle = () => {
        let arr = emojis;
        for (let i = 0; i < arr.length; i++) {
            let j = parseInt((Math.random() * (arr.length - i))) + i;

            let tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        emojis = arr;
    }

    shuffle();
    let defaultModal = $state(false);
</script>

<div class="flex flex-col gap-5 h-full">
    <div class="flex gap-[2vw]">
        <!--{#if emoji === ''}-->
        <!--    🐵-->
        <!--{:else}-->
        <!--    🙈-->
        <!--{/if}       -->

        <!--                Green Circle    🟢-->
        <!--                Red Circle      🔴-->
        <!--                Blue Circle     🔵-->
        <!--                Orange Circle   🟠-->
        <!--                Yellow Circle   🟡-->

        {#each Array.from({length: CODE_LENGTH}) as _, index}
            {#if success === null}
                <span class="flex-1 p-2 text-5xl select-none text-center">
                     {#if index < password.length}🟡{:else}🔵{/if}
                </span>
            {:else}
                <span class="flex-1 p-2 text-5xl select-none text-center">
                    {#if blink === true}
                        {#if success === true}🟢{:else if success === false}🔴{/if}
                    {:else}
                        🔵
                    {/if}
                </span>
            {/if}
        {/each}

        <button disabled={password.length <= 0 || password.length >= CODE_LENGTH}
                type="button"
                class="flex-1 rounded-2xl p-2 text-5xl select-none dark:shadow-cyan-500/25 dark:inset-shadow-cyan-500/25 not-active:shadow-md active:inset-shadow-sm disabled:shadow-none disabled:inset-shadow-none"
                onclick={removeEmoji}>
            🔙
        </button>
    </div>

    <Hr class="m-0 p-0"/>

    <div id="keyboard" class="flex-1 flex flex-col gap-[2vh]">
        {#each emoji_rows as row}
            <div class="flex-1 flex gap-[2vw]">
                {#each row as emoji}
                    <button disabled={password.length >= CODE_LENGTH}
                            type="button"
                            class="p-2 flex-1 dark:shadow-cyan-500/25 dark:inset-shadow-cyan-500/25 not-active:shadow-md active:inset-shadow-sm rounded-2xl text-5xl select-none disabled:shadow-none disabled:inset-shadow-none"
                            onclick={() => addEmoji(emoji)}>
                        {emoji}
                    </button>
                {/each}
            </div>
        {/each}
    </div>
</div>