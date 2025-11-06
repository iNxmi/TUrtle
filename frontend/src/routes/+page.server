import { redirect } from '@sveltejs/kit';

// redirect so that when users go to http://website they get redirected to http://website/dashboard which should be the landing page
export function load() {
  throw redirect(303, '/dashboard');
}