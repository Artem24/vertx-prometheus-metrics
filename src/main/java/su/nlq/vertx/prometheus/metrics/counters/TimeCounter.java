package su.nlq.vertx.prometheus.metrics.counters;

import io.prometheus.client.Counter;
import io.vertx.core.net.SocketAddress;
import org.jetbrains.annotations.NotNull;
import su.nlq.vertx.prometheus.metrics.PrometheusMetrics;
import su.nlq.vertx.prometheus.metrics.Stopwatch;

public final class TimeCounter {
  private final @NotNull Counter counter;
  private final @NotNull String localAddress;

  public TimeCounter(@NotNull String name, @NotNull String localAddress) {
    this.localAddress = localAddress;
    counter = Counter.build("vertx_" + name + "_time", "Processing time (μs)")
        .labelNames("local_address", "remote_address").create();
  }

  public void apply(@NotNull SocketAddress remoteAddress, @NotNull Stopwatch stopwatch) {
    counter.labels(localAddress, remoteAddress.toString()).inc(stopwatch.stop());
  }

  public @NotNull TimeCounter register(@NotNull PrometheusMetrics metrics) {
    metrics.register(counter);
    return this;
  }
}