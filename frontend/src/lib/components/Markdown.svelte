<script>
    import {marked} from 'marked';

    const {content} = $props();
</script>

<article class="prose">
    {@html marked.parse(content)}
</article>