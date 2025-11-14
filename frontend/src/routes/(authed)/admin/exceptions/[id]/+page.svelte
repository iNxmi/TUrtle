<script>
    import {Label, Input, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    const exception = data.user;
</script>

<form class="flex flex-col gap-5">
    <Label>
        <span>{m.admin_exceptions_id__id_label()}</span>
        <Input type="text" value={exception.id} disabled/>
    </Label>

    <div class="flex gap-5">
        <Label class="flex-1">
            <span>{m.admin_exceptions_id__endpoint_label()}</span>
            <Input ype="text" value={exception.endpoint} disabled/>
        </Label>

        <Label class="flex-1">
            <span>{m.admin_exceptions_id__exception_label()}</span>
            <Input type="text" value={exception.exception} disabled/>
        </Label>
    </div>

    <Label>
        <span>{m.admin_exceptions_id__message_label()}</span>
        <Input type="text" value={exception.message} disabled/>
    </Label>

    <Label>
        <span>{m.admin_exceptions_id__stack_trace_label()}</span>
        <Textarea class="w-full" value={exception.stackTrace} disabled/>
    </Label>

    <Label>
        <span>{m.admin_exceptions_id__created_at_label()}</span>
        <Input type="text" value={(new Date(exception.createdAt)).toLocaleString()} disabled/>
    </Label>
</form>