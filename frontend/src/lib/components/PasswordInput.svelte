<script>
    import {ButtonGroup, Input, InputAddon} from "flowbite-svelte";
    import {EyeOutline, EyeSlashOutline} from "flowbite-svelte-icons";

    let {
        disabled = $bindable(false),
        value = $bindable(""),
        required
    } = $props();

    let visible = $state(false);

    function toggle(event) {
        event.preventDefault();
        visible = !visible;
    }
</script>

<ButtonGroup class="w-full">
    <Input bind:disabled={disabled} bind:value={value} type={visible ? "text" : "password"} required={required}/>
    <InputAddon onclick={toggle}>
        {#if visible}
            <EyeOutline/>
        {:else}
            <EyeSlashOutline/>
        {/if}
    </InputAddon>
</ButtonGroup>