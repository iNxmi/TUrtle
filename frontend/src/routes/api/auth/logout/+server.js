export async function POST({_, fetch}) {
    return await fetch('http://backend:8080/api/auth/logout', {
        method: 'GET'
    });
}
