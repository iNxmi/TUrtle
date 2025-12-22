<script>
	import { span } from "flowbite-svelte";

    const emojis = ["😈", "😃", "🎩", "👽", "💩", "❤️", "💎", "👂", "👍", "🐋", "🐶", "🐸", "❄", "🎉", "💿",
                    "🍉", "☎", "🎥", "✂", "⚽", "🚀", "💄", "🌂", "🍄", "🍀", "🚗", "🍕", "🍔", "🍨", "💣","🐧", "💼", "🌍", "🐝", "🏠", "⏰"];
    let input = "";
    const addEmoji = e => {
        input += e.target.textContent;
    }

    //richtige Backend logik noch implementieren
    const submitInput = () => {
        console.log(input);
        input = "";
    }
</script>

<section>
    <div id="my-form" class="input-box text-2xl mb-[0.5rem]">
        <form class="form-container" on:submit|preventDefault={submitInput}>
            <input type="text" maxlength=6 name="input-box" required bind:value={input} class="input-box rounded-xl text-2xl h-[4rem] w-[22.7rem] mr-[0.3rem]" />
            <button type="submit" class="btn bg-green-500 rounded-xl text-2xl p-[0.2em] h-[4rem] w-[7rem]">Send</button>
        </form>
    </div>
    <div id="emoji-cont" class="keyboard w-[30rem]">
        {#each emojis as emoji}
            <button type="button" class="key w-[4rem] h-[4rem] m-[0.5rem] rounded-lg bg-neutral-100 text-2xl" on:click={addEmoji}>{emoji}</button>
        {/each}
    </div>
</section>

