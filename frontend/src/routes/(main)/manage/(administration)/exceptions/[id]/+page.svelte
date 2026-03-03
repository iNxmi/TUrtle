<script>
    import {Input, Textarea} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let exception = $derived(data.exception);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_exceptions_label_id()}</span>
            <Input type="text" value={exception.id} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_exceptions_label_endpoint()}</span>
                <Input type="text" value={exception.endpoint} disabled/>
            </div>

            <div class="flex-1">
                <span>{m.manage_exceptions_label_exception()}</span>
                <Input type="text" value={exception.exception} disabled/>
            </div>
        </div>

        <div>
            <span>{m.manage_exceptions_label_message()}</span>
            <Input type="text" value={exception.message} disabled/>
        </div>

        <div>
            <span>{m.manage_exceptions_label_stack_trace()}</span>
            <Textarea class="w-full" value={exception.stackTrace} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_exceptions_label_created_at()}</span>
                <Input type="text" value={(new Date(exception.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_exceptions_label_updated_at()}</span>
                <Input type="text" value={(new Date(exception.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>