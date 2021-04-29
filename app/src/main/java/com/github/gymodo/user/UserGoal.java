package com.github.gymodo.user;

public enum UserGoal {

    BUILD_MUSCLE,
    LOSE_FAT,
    GET_HARDY,
    ;

    @Override
    public String toString() {
        switch (this) {
            case BUILD_MUSCLE:
                return "Build muscle";
            case LOSE_FAT:
                return "Lose fat";
            case GET_HARDY:
                return "Get hardy";
        }
        return "Unknown";
    }
}
