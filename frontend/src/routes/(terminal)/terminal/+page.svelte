<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";
    import { Twemoji } from '@aurelle/svelte-twemoji';


    let emojis = $state(["😈", "😃", "🎩", "👽", "💩", "❤️", "💎", "👂", "👍", "🐋", "🐶", "🐸", "❄", "🎉", "💿",
                    "🍉", "☎", "🎥", "✂", "⚽", "🚀", "💄", "🌂", "🍄", "🍀", "🚗", "🍕", "🍔", "🍨", "💣","🐧", "💼", "🌍", "🐝", "🏠", "⏰"]);

    let password = $state(["", "", "", "", ""]);
    let password_index = 0;

    
    //teilt array in sechs reihen aus je sechs elementen für die emoji tastatur auf
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
            <div class="self-center w-full mt-3 mb-3">
                <button type="button" class="flex flex-col  bg-[#FF6A00] rounded-xl w-full h-[5rem] justify-center">Problem melden</button>
            </div>
        </div>
    </div>
    <div class="container h-screen flex justify-center items-center">
        <div class="login-box bg-neutral-100 rounded-xl shadow-xl border-1 border-neutral-300 w-[70vw] max-w-[40rem] h-[95vh] flex flex-col justify-around items-center p-10">
            <div class="input-row rounded-xl w-full bg-[white] flex justify-between p-[0.5rem] mb-[2rem]">
                {#each password as emoji}
                    <span class="text-4xl">
	                    {#if emoji === ''}
		                    <Twemoji emoji="🐵" size={48}/>
	                    {:else}
                            <Twemoji emoji="🙈" size={48}/>
	                    {/if}
                    </span>
                {/each}
                <button type = "button" class="text-4xl" onclick={backspace}><Twemoji emoji="🔙" size={48}/></button>
            </div>
            <div class="emoji-keyboard w-full h-full flex flex-col justify-around">
                {#each emoji_rows as row}
                    <div class="emoji-row w-full flex justify-between p-[0.5rem]">
                        {#each row as emoji}
                            <button type="button" class="key w-[3rem] h-[3rem] rounded-lg bg-neutral-100 text-5xl" onclick={() => addEmoji({emoji})}><Twemoji emoji={emoji} size={48}/></button>
                        {/each}
                    </div>
                {/each}
            </div>
        </div>
    </div>
</div>