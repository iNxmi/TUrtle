<script>
    import request from "$lib/api/api.js";

    import {Hr} from "flowbite-svelte";

    let {data} = $props();
    let emojis = $derived(data.emojis);

    let password = $state(["", "", "", "", ""]);
    let password_index = 0;

    //teilt array in sechs reihen aus je sechs elementen auf für die emoji tastatur
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
        password[password_index] = emoji;
        if (password_index < 5) {
            password[password_index] = emoji;
            password_index += 1;
        }
        if (password_index === 5) {
            submitInput();
        }
    }

    async function submitInput() {
        const payload = {
            emojis: getPassword()
        };

        const response = await request('/hardware/door/emojis', {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {'Content-Type': 'application/json'}
        });

        shuffle();
        password = ["", "", "", "", ""];
        password_index = 0;
    }

    const backspace = () => {
        if (password_index >= 1)
            password_index -= 1;

        password[password_index] = "";
    }
    const getPassword = () => {
        let pwd = "";
        for (let i = 0; i < 5; i++)
            pwd += password[i];

        return pwd;
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
        {#each password as emoji}
            <span class="flex-1 p-2 text-5xl select-none text-center">
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

                {#if emoji === ''}
                    🔵
                {:else}
                    🟡
                {/if}
            </span>
        {/each}
        <button type="button"
                class="flex-1 rounded-2xl p-2 text-5xl select-none dark:shadow-cyan-500/25 dark:inset-shadow-cyan-500/25 not-active:shadow-md active:inset-shadow-sm"
                onclick={backspace}>🔙
        </button>
    </div>

    <Hr class="m-0 p-0"/>

    <div id="keyboard" class="flex-1 flex flex-col gap-[2vh]">
        {#each emoji_rows as row}
            <div class="flex-1 flex gap-[2vw]">
                {#each row as emoji}
                    <button type="button"
                            class="p-2 flex-1 dark:shadow-cyan-500/25 dark:inset-shadow-cyan-500/25 not-active:shadow-md active:inset-shadow-sm rounded-2xl text-5xl select-none"
                            onclick={() => addEmoji(emoji)}>
                        {emoji}
                    </button>
                {/each}
            </div>
        {/each}
    </div>
</div>