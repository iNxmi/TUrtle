<script>
	import { span } from "flowbite-svelte";

    const emojiCodes = [128515, 127968, 128066, 128142, 128125, 128640, 128132, 128191, 128520, 9917, 9986, 127909, 128663, 127812, 128056, 127829, 128163, 127817, 127757, 9742, 127828, 127848, 128029, 128169, 128077, 128188, 128039, 127881, 127808, 127913, 10052, 10084, 127746, 128054, 9200, 128011];
    let emojis = [];
    for(let i=0; i<emojiCodes.length; i++){
        emojis[i] = String.fromCodePoint(emojiCodes[i]);
    }

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
            <input type="text" name="input-box" required bind:value={input} class="input-box rounded-xl text-2xl h-[4rem] w-[22.7rem] mr-[0.3rem]" />
            <button type="submit" class="btn bg-green-500 rounded-xl text-2xl p-[0.2em] h-[4rem] w-[7rem]">Send</button>
        </form>
    </div>
    <div id="emoji-cont" class="keyboard w-[30rem]">
        {#each emojis as emoji}
            <button type="button" class="key w-[4rem] h-[4rem] m-[0.5rem] rounded-lg bg-neutral-100 text-2xl" on:click={addEmoji}>{emoji}</button>
        {/each}
    </div>
</section>

