import {getPage} from "$lib/utils.js";
import {StatisticQueries} from "$lib/api";

export async function load({url}) {
    const page = await getPage(url, "/api/statistic-queries")
    const types = await getTypes();
    return {page: page, types: types};
}

async function getTypes() {
    const response = await StatisticQueries.type();
    return await response.json();
}