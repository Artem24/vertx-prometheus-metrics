package io.vertx.ext.prometheus.metrics.counters;

import io.prometheus.client.Counter;
import io.vertx.ext.prometheus.metrics.PrometheusMetrics;
import io.vertx.ext.prometheus.metrics.Stopwatch;
import org.jetbrains.annotations.NotNull;

public final class TimeCounter {
  private final @NotNull Counter counter;
  private final @NotNull String localAddress;

  public TimeCounter(@NotNull String name, @NotNull String localAddress) {
    this.localAddress = localAddress;
    counter = Counter.build("vertx_" + name + "_time", "Processing time (μs)")
        .labelNames("local_address").create();
  }

  public void apply(@NotNull Stopwatch stopwatch) {
    counter.labels(localAddress).inc(stopwatch.stop());
  }

  public @NotNull TimeCounter register(@NotNull PrometheusMetrics metrics) {
    metrics.register(counter);
    return this;
  }
}
