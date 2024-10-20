import {Workout} from "../typing";

export function isDateInThisWeek(date: Date){
    const todayObj = new Date();
    todayObj.setHours(0, 0, 0, 0);
    const dayOfWeek = todayObj.getDay();

    // Calculate the first day of the week (Sunday as the first)
    const firstDayOfWeek = new Date(todayObj);
    firstDayOfWeek.setDate(todayObj.getDate() - dayOfWeek);

    // Calculate the last day of the week (Saturday)
    const lastDayOfWeek = new Date(firstDayOfWeek);
    lastDayOfWeek.setDate(firstDayOfWeek.getDate() + 6);

    const inputDate = new Date(date);

    // Check if the input date is within the current week
    return inputDate >= firstDayOfWeek && inputDate <= lastDayOfWeek;
}


export function getWeekOf(date: Date, weekOf: string[]) {
    const firstDayOfWeek = new Date(date);

    // Calculate the last day of the week (Sunday)
    const lastDayOfWeek = new Date(firstDayOfWeek);
    lastDayOfWeek.setDate(firstDayOfWeek.getDate() + 6);

    return weekOf.push(firstDayOfWeek.toDateString(), lastDayOfWeek.toDateString());

}

export function isDateInSelectedWeek(date: Date, selectedWeek: Date){
    date.setHours(0, 0, 0, 0);
    const firstDayOfWeek = new Date(selectedWeek);
    firstDayOfWeek.setHours(0, 0, 0, 0);
    const lastDayOfWeek = new Date(selectedWeek);
    lastDayOfWeek.setHours(0, 0, 0, 0);
    lastDayOfWeek.setDate(selectedWeek.getDate() + 6);

    // Check if the input date is within the current week
    return date >= firstDayOfWeek && date <= lastDayOfWeek;
}
export function sortWorkouts(workouts: Workout[]): Workout[] {
    return workouts.sort((a, b) => {
        return new Date(b.workoutDate).getTime() - new Date(a.workoutDate).getTime();
    });
}

export function sortWorkoutsAsc(workouts: Workout[]): Workout[] {
    return workouts.sort((a, b) => {
        return new Date(a.workoutDate).getTime() - new Date(b.workoutDate).getTime();
    });
}