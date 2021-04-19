package com.github.gymodo.food;

/**
 * The meal type.
 *
 * @see Food
 * @see Meal
 * @see Diet
 */
public enum MealType {
    BREAKFAST,
    LAUNCH,
    DINNER,
    SNACK,
    ;

    @Override
    public String toString() {
        switch (this) {
            case BREAKFAST:
                return "Breakfast";
            case LAUNCH:
                return "Launch";
            case DINNER:
                return "Dinner";
            case SNACK:
                return "Snack";
        }
        return "Unknown";
    }
}
