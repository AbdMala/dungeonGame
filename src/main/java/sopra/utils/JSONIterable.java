package sopra.utils;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Wrapper around {@link JSONObject} to make it iterable.
 *
 */
public final class JSONIterable implements Iterable<JSONObject> {

  private final Queue<JSONObject> objects;

  private JSONIterable(final JSONArray array) {
    this.objects = new ArrayDeque<>(array.length());
    IntStream.range(0, array.length()).mapToObj(array::getJSONObject).forEach(this.objects::add);
  }

  public static JSONIterable from(final JSONArray json) {
    return new JSONIterable(json);
  }

  @NotNull
  @Override
  public Iterator<JSONObject> iterator() {
    return this.objects.iterator();
  }
}
