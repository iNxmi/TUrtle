<script>
    import {Timepicker} from "flowbite-svelte";

    let {
        disabled = false,
        value = $bindable(new Date())
    } = $props();

    let time = $state(format(value));

    function format(date) {
        const hours = String(date.getHours()).padStart(2, "0");
        const minutes = String(date.getMinutes()).padStart(2, "0");
        return `${hours}:${minutes}`;
    }

    $effect(() => {
        const [hoursString, minutesString] = time.split(":");
        const hours = Number(hoursString);
        const minutes = Number(minutesString);

        if (value.getHours() === hours && value.getMinutes() === minutes)
            return;

        const newDate = new Date(value);
        newDate.setHours(hours, minutes, 0, 0)

        value = newDate;
    });

    $effect(() => {
        const newTime = format(value);
        if (newTime === time)
            return;

        time = newTime;
    });
</script>

<Timepicker disabled={disabled} bind:value={time}/>