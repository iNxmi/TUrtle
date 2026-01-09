<script>
    import TUrtleLogo from "$lib/components/TUrtleLogo.svelte";


    let emojis = $state(["😈", "😃", "🎩", "👽", "💩", "❤️", "💎", "👂", "👍", "🐋", "🐶", "🐸", "❄", "🎉", "💿",
                    "🍉", "☎", "🎥", "✂", "⚽", "🚀", "💄", "🌂", "🍄", "🍀", "🚗", "🍕", "🍔", "🍨", "💣","🐧", "💼", "🌍", "🐝", "🏠", "⏰"]);

    let password = $state(["", "", "", "", ""]);
    let password_index = 0;


    const addEmoji = e => {
        password[password_index] = e.target.textContent;
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

    const backspace = () =>{
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
        <div class="login-box bg-neutral-100 rounded-xl shadow-xl border-1 border-neutral-300 w-[70vw] h-[95vh] flex flex-col justify-around items-center p-10">
            <div class="input-row rounded-xl w-full bg-[white] flex justify-between p-[0.5rem] mb-[2rem]">
                {#each password as emoji}
                    <span class="text-4xl"> {emoji === "" ? "🐵" : "🙈"} </span>
                {/each}
                <button type = "button" class="text-4xl" onclick={backspace}>🔙</button>
            </div>
            <div class="emoji-keyboard w-fill flex flex-wrap justify-around">
                {#each emojis as emoji}
                    <button type="button" class="key w-[3rem] h-[3rem]  m-[0.9rem] rounded-lg bg-neutral-100 text-4xl" onclick={addEmoji}>{emoji}</button>
                {/each}
            </div>
        </div>
    </div>
</div>
