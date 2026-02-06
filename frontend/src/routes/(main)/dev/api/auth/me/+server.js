import { json } from '@sveltejs/kit';
export async function GET() {
	return json({
		role: 'ADMINISTRATOR',
		firstName: 'Jan',
		lastName: 'Rappe'
	});
}
