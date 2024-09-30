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
    memberSince: Date;
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
    workoutDate: Date;
    exercises?: number;
    volume?: number;
}

export type ProfileDetails = {
    name?: string
    email?: string
    memberSince: Date;
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
    profile: ProfileDetails;
    pfp?: string;
}

export type HealthInfoWidget = {
    healthInfo: HealthInfo;
}