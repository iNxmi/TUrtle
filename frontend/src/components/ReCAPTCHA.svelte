<script>
    import { onMount } from 'svelte';

    let recaptchaWidgetId;
    let token = '';

    onMount(() => {
        const script = document.createElement('script');
        script.src = 'https://www.google.com/recaptcha/api.js?render=explicit';
        script.async = true;
        script.defer = true;
        script.onload = () => {
            window.grecaptcha.ready(() => {
                recaptchaWidgetId = window.grecaptcha.render('recaptcha-container', {
                    sitekey: '6Lc9XAksAAAAALGRyOGgkpqbnftTqtSRREyzZtaC',
                    // theme: 'dark',
                    callback: (response) => {
                        token = response;
                        // console.log('reCAPTCHA token:', token);
                    }
                });
            });
        };
        document.body.appendChild(script);
    });
</script>

<div id="recaptcha-container"></div>