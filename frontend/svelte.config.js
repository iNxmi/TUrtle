/** @type {import('@sveltejs/kit').Config} */
import adapter from '@sveltejs/adapter-node';
const config = {
	kit: {
		adapter: adapter(),
		alias: {}
	}
};

export default config;
