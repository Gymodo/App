package com.github.gymodo.food;

import java.io.Serializable;

/**
 * The meal type.
 *
 * @see Food
 * @see Meal
 * @see Diet
 */
public enum MealType implements Serializable {
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
