<script>
    import {m} from "$lib/paraglide/messages.js";
    import {Button, Input} from "flowbite-svelte";
    import {LockOpenOutline} from "flowbite-svelte-icons";
    import EntityPage from "$lib/components/EntityPage.svelte";
    import {Lockers, StatisticQueries} from "$lib/api";
    import {PlayOutline} from "flowbite-svelte-icons";

    let {data} = $props();
    let entity = $derived(data.entity);

    const items = $derived([{
        label: m.manage_lockers_label_id(),
        field: "id",
        component: Input,
        props: {
            value: entity.id
        }
    }, {
        label: m.manage_lockers_label_name(),
        field: "name",
        editable: true,
        component: Input,
        props: {
            value: entity.name
        }
    }, {
        label: m.manage_lockers_label_index(),
        field: "index",
        editable: true,
        component: Input,
        props: {
            value: entity.index
        }
    }, [{
        label: m.manage_lockers_label_software_unlockable(),
        field: "softwareUnlockable",
        editable: true,
        component: Input,
        props: {
            value: entity.softwareUnlockable
        }
    }, {
        label: m.manage_lockers_label_locked(),
        field: "locked",
        editable: true,
        component: Input,
        props: {
            value: entity.locked
        }
    }], [{
        label: m.manage_item_categories_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: entity.createdAt
        }
    }, {
        label: m.manage_item_categories_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: entity.updatedAt
        }
    }]]);

    const controls = [{
        component: Button,
        icon: LockOpenOutline,
        props: {
            color: "yellow",
            onclick: async () => {
                const response = await Lockers.unlock(entity.id)
                if(!response.ok) {
                    alert(`Error: ${response.message}`);
                    return;
                }

                alert("Success");
            }
        }
    }];
</script>

<EntityPage items={items}
            controls={controls}
            onPatch={(payload) => Lockers.patch(entity.id, payload)}
            onDelete={() => Lockers.delete(entity.id)}
/>