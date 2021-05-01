package com.github.gymodo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A collection of useful methods for firebase.
 */
public abstract class DatabaseUtil {

    /**
     * Gets a object by id.
     *
     * @param collection The database collection.
     * @param id         The id
     * @param valueType  The class type.
     * @param <T>        The class type.
     * @return A task with object as result.
     */
    public static <T> Task<T> getByID(String collection, @NonNull String id, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection)
                .document(id)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        return Tasks.forResult(query.toObject(valueType));
                    }
                    return Tasks.forCanceled();
                });
    }

    /**
     * Saves (creates) an object on the database.
     *
     * @param collection The database collection.
     * @param object     The object.
     * @param valueType  The object class.
     * @param <T>        The object type.
     * @return A task.
     */
    public static <T> Task<Void> saveObject(String collection, @NonNull T object, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection)
                .document()
                .set(object);
    }

    /**
     * Updates an object on the database.
     *
     * @param collection The database collection.
     * @param id         The id of the object.
     * @param object     the object.
     * @param valueType  The object class.
     * @param <T>        The object type.
     * @return A task.
     */
    public static <T> Task<Void> updateObject(String collection, @NonNull String id, @NonNull T object, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection)
                .document(id)
                .set(object);
    }

    /**
     * Gets all the objects.
     *
     * @param collection The database collection.
     * @param valueType  The class type.
     * @param <T>        The class type.
     * @return A task with the list of objects as result.
     */
    public static <T> Task<List<T>> getAll(@NonNull String collection, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        return Tasks.forResult(query.toObjects(valueType));
                    }
                    return Tasks.forCanceled();
                });
    }

    /**
     * Gets a list of objects where the value matches..
     *
     * @param collection The database collection.
     * @param valueName  The value name.
     * @param valueName  The value
     * @param valueType  The class type.
     * @param <T>        The class type.
     * @return A task with the list of objects as result.
     */
    public static <T> Task<List<T>> getWhereValueIs(@NonNull String collection, String valueName, Object value, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection)
                .whereEqualTo(valueName, value)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        return Tasks.forResult(query.toObjects(valueType));
                    }
                    return Tasks.forCanceled();
                });
    }

    /**
     * Gets a list of objects where the value is greater .
     *
     * @param collection The database collection.
     * @param valueName  The value name.
     * @param valueName  The value
     * @param valueType  The class type.
     * @param <T>        The class type.
     * @return A task with the list of objects as result.
     */
    public static <T> Task<List<T>> getWhereValueIsGreatherOrEqual(@NonNull String collection, String valueName, Object value, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection)
                .whereGreaterThanOrEqualTo(valueName, value)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        return Tasks.forResult(query.toObjects(valueType));
                    }
                    return Tasks.forCanceled();
                });
    }

    /**
     * Gets a list of objects with the ids from the given list.
     *
     * @param collection The database collection.
     * @param ids        The list of ids
     * @param valueType  The class type.
     * @param <T>        The class type.
     * @return A task with the list of objects as result.
     */
    public static <T> Task<List<T>> getWhereIdIn(@NonNull String collection, @NonNull List<String> ids, @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(ids.isEmpty())
            return Tasks.forResult(new ArrayList<T>());

        return db.collection(collection)
                .whereIn(FieldPath.documentId(), ids)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        return Tasks.forResult(query.toObjects(valueType));
                    }
                    return Tasks.forCanceled();
                });
    }

    /**
     * Gets a list of values mapped from a list of objects.
     *
     * @param collection The database collection.
     * @param ids        The list of ids.
     * @param mapper     The mapper function.
     * @param valueType  The object type.
     * @param <T>        The object type.
     * @param <Result>   The result type.
     * @return A stream of results.
     */
    public static <T, Result> Task<Stream<Result>> getMappedWhereIn(@NonNull String collection,
                                                                    @NonNull List<String> ids,
                                                                    Function<T, Result> mapper,
                                                                    @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection).whereIn(FieldPath.documentId(), ids)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        List<T> list = query.toObjects(valueType);
                        return Tasks.forResult(list.parallelStream().map(mapper));
                    }
                    return Tasks.forCanceled();
                });
    }

    /**
     * Gets a list of ints mapped from a list of objects.
     *
     * @param collection The database collection.
     * @param ids        The list of ids.
     * @param mapper     The mapper function.
     * @param valueType  The object type.
     * @param <T>        The object type.
     * @return A stream of integers.
     */
    public static <T> Task<IntStream> getIntMappedWhereIn(@NonNull String collection,
                                                          @NonNull List<String> ids,
                                                          ToIntFunction<T> mapper,
                                                          @NonNull Class<T> valueType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        return db.collection(collection).whereIn(FieldPath.documentId(), ids)
                .get()
                .onSuccessTask(query -> {
                    if (query != null) {
                        List<T> list = query.toObjects(valueType);
                        return Tasks.forResult(list.parallelStream().mapToInt(mapper));
                    }

                    return Tasks.forCanceled();
                });
    }

    /**
     * Gets the sum of ints mapped from a list of objects.
     *
     * @param collection The database collection.
     * @param ids        The list of ids.
     * @param mapper     The mapper function.
     * @param valueType  The object type.
     * @param <T>        The object type.
     * @return The sum.
     */
    public static <T> Task<Integer> getMappedSumWhereIn(@NonNull String collection,
                                                        @NonNull List<String> ids,
                                                        ToIntFunction<T> mapper,
                                                        @NonNull Class<T> valueType) {
        return getIntMappedWhereIn(collection, ids, mapper, valueType).onSuccessTask(result -> {
            if (result != null) {
                return Tasks.forResult(result.sum());
            }
            return Tasks.forCanceled();
        });
    }
}
