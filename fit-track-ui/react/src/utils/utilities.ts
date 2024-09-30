
export function isDateInThisWeek(date: Date, weekOf: string[]){
    const todayObj = new Date();
    todayObj.setHours(0, 0, 0, 0);
    const dayOfWeek = todayObj.getDay(); // Sunday is 0, Monday is 1, etc.

    // Calculate the first day of the week (Monday as the first day)
    const firstDayOfWeek = new Date(todayObj);
    firstDayOfWeek.setDate(todayObj.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek));

    // Calculate the last day of the week (Sunday)
    const lastDayOfWeek = new Date(firstDayOfWeek);
    lastDayOfWeek.setDate(firstDayOfWeek.getDate() + 6);

    // Convert the input date string to a Date object (if needed)
    const inputDate = new Date(date);
    weekOf.push(firstDayOfWeek.toDateString(), lastDayOfWeek.toDateString());

    // Check if the input date is within the current week
    return inputDate >= firstDayOfWeek && inputDate <= lastDayOfWeek;
}