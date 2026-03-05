<script>
    import {Input, Select, Textarea} from "flowbite-svelte";
    import {m} from '$lib/paraglide/messages.js';
    import EntityPage from "$lib/components/EntityPage.svelte";

    let {data} = $props();
    let ticket = $derived(data.ticket);

    const statusItems = $derived(data.statuses.map((status) => ({
        value: status,
        name: status
    })));

    const urgencyItems = $derived(data.urgencies.map((urgency) => ({
        value: urgency.id,
        name: urgency.name
    })));

    const categoryItems = $derived(data.categories.map((category) => ({
        value: category.id,
        name: category.name
    })));

    const items = $derived([{
        label: m.manage_support_tickets_label_id(),
        field: "id",
        component: Input,
        props: {
            value: ticket.id
        }
    }, [{
        label: m.manage_support_tickets_label_urgency(),
        field: "urgencyId",
        component: Select,
        props: {
            value: ticket.urgencyId,
            items: urgencyItems
        }
    }, {
        label: m.manage_support_tickets_label_category(),
        field: "categoryId",
        component: Select,
        props: {
            value: ticket.categoryId,
            items: categoryItems
        }
    }], {
        label: m.manage_support_tickets_label_email(),
        field: "email",
        component: Input,
        props: {
            value: ticket.email
        }
    }, {
        label: m.manage_support_tickets_label_subject(),
        field: "subject",
        component: Input,
        props: {
            value: ticket.subject
        }
    }, {
        label: m.manage_support_tickets_label_content(),
        field: "content",
        component: Textarea,
        props: {
            value: ticket.content
        }
    }, {
        label: m.manage_support_tickets_label_status(),
        field: "status",
        editable: true,
        component: Select,
        props: {
            value: ticket.status,
            items: statusItems
        }
    }, [{
        label: m.manage_support_tickets_label_created_at(),
        field: "createdAt",
        component: Input,
        props: {
            value: ticket.createdAt
        }
    }, {
        label: m.manage_support_tickets_label_updated_at(),
        field: "updatedAt",
        component: Input,
        props: {
            value: ticket.updatedAt
        }
    }]]);
</script>

<EntityPage items={items} onPatch={(payload) => SupportTickets.patch(ticket.id, payload)}/>
<!--<Card>-->
<!--    <form class="flex flex-col gap-5">-->

<!--        <div class="flex gap-5">-->
<!--            <div class="flex-1">-->
<!--                <span>{m.manage_support_tickets_label_urgency()}</span>-->
<!--                <Select items={urgencyItems} value={ticket.urgencyId} disabled/>-->
<!--            </div>-->

<!--            <div class="flex-1">-->
<!--                <span>{m.manage_support_tickets_label_category()}</span>-->
<!--                <Select items={categoryItems} value={ticket.categoryId} disabled/>-->
<!--            </div>-->
<!--        </div>-->

<!--    </form>-->
<!--</Card>-->