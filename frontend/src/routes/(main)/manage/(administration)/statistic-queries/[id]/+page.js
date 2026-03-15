import {StatisticQueries} from "$lib/api";

export async function load({params}) {
    const entity = await getStatisticQuery(params.id);
    const types = await getTypes();
    return {entity: entity, types: types};
}

async function getStatisticQuery(id) {
    const response = await StatisticQueries.get(id);
    return await response.json();
}

async function getTypes() {
    const response = await StatisticQueries.type();
    return await response.json();
}