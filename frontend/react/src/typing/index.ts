export interface Customer {
    id?: number
    name?: string
    email?: string
    gender?: string
    age?: number
    roles?: string[]
    username?: string
    profileImageId?: string
    workouts?: Workout[]
}


export interface Workout {
    customerId?: string
    workoutType?: string
    calories?: number
    durationMinutes?: number
}