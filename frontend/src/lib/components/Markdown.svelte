<script>
    import Markdown from 'svelte-exmarkdown';
    import {gfmPlugin } from 'svelte-exmarkdown/gfm'
    let { md } = $props();
    const plugins = [gfmPlugin()];
</script>
<div class="markdown">
    <Markdown {md} {plugins} />
</div>