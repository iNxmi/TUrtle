<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Input, Textarea} from "flowbite-svelte";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {Configuration} from "$lib/api";

    let {data} = $props();
    let configuration = $derived(data.configuration);

    const items = [{
        label: m.manage_configuration_label_id(),
        field: "id",
        component: Input,
        props: {
            value: configuration.id
        }
    }, {
        label: m.manage_configuration_label_key(),
        field: "key",
        component: Input,
        props: {
            value: configuration.key
        }
    }, {
        label: m.manage_configuration_label_type(),
        field: "type",
        component: Input,
        props: {
            value: configuration.type
        }
    }, {
        label: m.manage_configuration_label_value(),
        field: "permissions",
        editable: true,
        component: Input,
        props: {
            value: configuration.value
        }
    }, [{
        label: m.manage_configuration_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: configuration.createdAt
        }
    }, {
        label: m.manage_configuration_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: configuration.updatedAt
        }
    }]];
</script>

<EntityPage items={items} onPatch={(payload) => Configuration.patch(configuration.id, payload)}/>

<!--
<script>
    import {Input, Textarea} from "flowbite-svelte";
    import Card from "$lib/components/Card.svelte";
    import {m} from '$lib/paraglide/messages.js';

    let {data} = $props();
    let configuration = $derived(data.configuration);
</script>

<Card>
    <form class="flex flex-col gap-5">
        <div>
            <span>{m.manage_configuration_label_id()}</span>
            <Input type="text" value={configuration.id} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_configuration_label_key()}</span>
                <Input type="text" value={configuration.key} disabled/>
            </div>

            <div class="flex-1">
                <span>{m.manage_configuration_label_type()}</span>
                <Input type="text" value={configuration.type} disabled/>
            </div>
        </div>

        <div>
            <span>{m.manage_configuration_label_value()}</span>
            <Input type="text" value={configuration.value} disabled/>
        </div>

        <div>
            <span>{m.manage_configuration_label_visibility()}</span>
            <Input type="text" value={configuration.visibility} disabled/>
        </div>

        <div class="flex gap-5">
            <div class="flex-1">
                <span>{m.manage_configuration_label_created_at()}</span>
                <Input type="text" value={(new Date(configuration.createdAt)).toLocaleString()} disabled/>
            </div>
            <div class="flex-1">
                <span>{m.manage_configuration_label_updated_at()}</span>
                <Input type="text" value={(new Date(configuration.updatedAt)).toLocaleString()} disabled/>
            </div>
        </div>
    </form>
</Card>-->
