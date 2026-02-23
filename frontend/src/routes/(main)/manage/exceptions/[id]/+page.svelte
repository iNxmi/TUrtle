<script>
    import {Input, div, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let exception = $derived(data.exception);
</script>

<form class="flex flex-col gap-5">
    <div>
        <span>{m.admin_exceptions_id__id_label()}</span>
        <Input type="text" value={exception.id} disabled/>
    </div>

    <div class="flex gap-5">
        <div class="flex-1">
            <span>{m.admin_exceptions_id__endpoint_label()}</span>
            <Input ype="text" value={exception.endpoint} disabled/>
        </div>

        <div class="flex-1">
            <span>{m.admin_exceptions_id__exception_label()}</span>
            <Input type="text" value={exception.exception} disabled/>
        </div>
    </div>

    <div>
        <span>{m.admin_exceptions_id__message_label()}</span>
        <Input type="text" value={exception.message} disabled/>
    </div>

    <div>
        <span>{m.admin_exceptions_id__stack_trace_label()}</span>
        <Textarea class="w-full" value={exception.stackTrace} disabled/>
    </div>

    <div>
        <span>{m.admin_exceptions_id__created_at_label()}</span>
        <Input type="text" value={(new Date(exception.createdAt)).toLocaleString()} disabled/>
    </div>
</form>