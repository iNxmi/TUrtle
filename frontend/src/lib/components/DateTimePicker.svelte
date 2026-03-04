<script>
    import {Datepicker} from "flowbite-svelte";
    import TimePicker from "$lib/components/TimePicker.svelte";

    let {
        value = $bindable(new Date()),
        class: className = "",
        ...rest
    } = $props();

</script>

<div class="flex gap-0 w-full">
    <Datepicker bind:value={value} inputClass="py-2.5" {...rest}/>
    <TimePicker bind:value={value} inputClass="rounded-l-none" {...rest}/>
</div>