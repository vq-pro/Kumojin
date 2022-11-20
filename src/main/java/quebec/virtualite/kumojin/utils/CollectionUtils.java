package quebec.virtualite.kumojin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class CollectionUtils
{
    @SafeVarargs
    public static <T> List<T> list(T... items)
    {
        return new ArrayList<>(List.of(items));
    }

    public static <I, O> List<O> transform(List<I> inputList, Function<I, O> mapperFunction)
    {
        return inputList.stream()
            .map(mapperFunction)
            .collect(toList());
    }
}
