package io.vertx.ext.prometheus.metrics.counters;

import io.prometheus.client.Counter;
import io.vertx.ext.prometheus.metrics.PrometheusMetrics;
import org.jetbrains.annotations.NotNull;

public final class TimeCounter {
  private final @NotNull Counter counter;
  private final @NotNull Counter.Child time;

  public TimeCounter(@NotNull String name, @NotNull String localAddress) {
    counter = Counter.build("vertx_" + name + "_time", "Processing time (us)")
        .labelNames("local_address").create();
    time = counter.labels(localAddress);
  }

  public void apply(@NotNull Stopwatch stopwatch) {
    time.inc(stopwatch.stop());
  }

  public @NotNull TimeCounter register(@NotNull PrometheusMetrics metrics) {
    metrics.register(counter);
    return this;
  }
}
