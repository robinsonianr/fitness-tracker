export interface Customer {
    id?: number;
    name?: string;
    email?: string;
    gender?: string;
    age?: number;
    weight?: number;
    height?: number;
    weightGoal?: number;
    activity?: string;
    bodyFat?: number;
    roles?: string[];
    username?: string;
    profileImageId?: string;
    workouts?: Workout[];
}


export interface Workout {
    id?: number;
    customerId?: string;
    workoutType?: string;
    calories?: number;
    durationMinutes?: number;
    workoutDate?: string;
}

export type ProfileDetails = {
    name?: string
    email?: string
}

export type HealthInfo = {
    age?: number
    gender?: string
    weight?: number
    height?: number
    weightGoal?: number
    activity?: string
    bodyFat?: number
}


export type ProfileDetailsWidget = {
    profile: ProfileDetails,
    pfp?: string | undefined,
}

export type HealthInfoWidget = {
    healthInfo: HealthInfo
}