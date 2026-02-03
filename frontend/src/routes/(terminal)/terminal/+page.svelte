<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import { Button, Modal } from "flowbite-svelte";

    let emojis = $state(["😈", "😃", "🎩", "👽", "💩", "❤️", "💎", "👂", "👍", "🐋", "🐶", "🐸", "❄", "🎉", "💿",
                    "🍉", "☎", "🎥", "✂", "⚽", "🚀", "💄", "🌂", "🍄", "🍀", "🚗", "🍕", "🍔", "🍨", "💣","🐧", "💼", "🌍", "🐝", "🏠", "⏰"]);

    let password = $state(["", "", "", "", ""]);
    let password_index = 0;

    
    //teilt array in sechs reihen aus je sechs elementen auf für die emoji tastatur
    const make_rows = arr => {
        let rows = [[], [], [], [], [], []];
        let row_number = 0;
        let index = 0;
        for(let i=0; i<arr.length; i++){
            rows[row_number][index] = arr[i];
            index++;
            if(index == 6){
                index = 0;
                row_number++;
            }
        }
        return rows;
    }
    let emoji_rows = $derived(make_rows(emojis));

    const addEmoji = emoji => {
        password[password_index] = emoji;
        password_index += 1;
        if(password_index==5){
            submitInput();
        }
    }

    //richtige Backend logik noch implementieren
    const submitInput = () => {
        console.log(getPassword());
        password = ["", "", "", "", ""];
        password_index = 0;
    }
    $inspect(password);

    const backspace = () => {
        if(password_index >= 1){
            password_index -= 1;
        }
        password[password_index] = "";
    }
    const getPassword = () => {
        let pwd = "";
        for(let i=0; i<5; i++){
            pwd += password[i];
        }
        return pwd;
    }
    const shuffle = () => {
        let arr = emojis;
        for(let i=0; i<arr.length; i++){
            let j = parseInt((Math.random() * (arr.length - i))) + i;
            let tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        emojis = arr;
    }

    let defaultModal = $state(false);

    shuffle();
    $inspect(emojis);
    $inspect(emoji_rows);
</script>

<div class="flex">
    <div class="flex flex-col gap-2 justify-between bg-neutral-100 w-[25vw] h-screen p-4 pb-1 pt-2">
        <div class="flex flex-col items-center">
            <TUrtleLogo />
        </div>
        <div>
            <div class="flex fkex-col w-full aspect-square rounded-xl bg-[#FF6A00] justify-center items-center">
                <p>QR-Code (?)</p>
            </div>
            <div class="self-center w-full mt-3 mb-3 bg-white text-gray-900">
                <Button onclick={() => (defaultModal = true)}>Problem melden</Button>
                <Modal title="Problem Melden" form bind:open={defaultModal} theme="light">
                </Modal>
            </div>
        </div>
    </div>
    <div class="container h-screen flex justify-center items-center">
        <div class="login-box bg-neutral-100 rounded-xl shadow-xl border-1 border-neutral-300 w-[70vw] max-w-[40rem] h-[95vh] flex flex-col justify-around items-center p-10">
            <div class="input-row rounded-xl w-full bg-[white] flex justify-between p-[0.5rem] mb-[2rem]">
                {#each password as emoji}
                    <span class="text-5xl">
	                    {#if emoji === ''}
		                    🐵
	                    {:else}
                            🙈
	                    {/if}
                    </span>
                {/each}
                <button type = "button" class="text-5xl" onclick={backspace}>🔙</button>
            </div>
            <div class="emoji-keyboard w-full h-full flex flex-col justify-around">
                {#each emoji_rows as row}
                    <div class="emoji-row w-full flex justify-between p-[0.5rem]">
                        {#each row as emoji}
                            <button type="button" class="key w-[3.5rem] h-[3.5rem] rounded-lg bg-neutral-100 text-5xl active:bg-neutral-300" onclick={() => addEmoji({emoji})}>{emoji}</button>
                        {/each}
                    </div>
                {/each}
            </div>
        </div>
    </div>
</div>