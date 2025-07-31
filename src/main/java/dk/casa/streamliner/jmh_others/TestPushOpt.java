package dk.casa.streamliner.jmh;

import dk.casa.streamliner.stream.IntPushStream;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
public class TestPushOpt extends TestBase {
	@Setup
	public void setUp() {
		super.setUp();
	}

	@Benchmark
	public int sum() {
		return IntPushStream.of(v).sum();
	}

	@Benchmark
	public int sumOfSquares() {
		return IntPushStream.of(v)
				.map(d -> d * d)
				.sum();
	}

	@Benchmark
	public int sumOfSquaresEven() {
		return IntPushStream.of(v)
				.filter(x -> x % 2 == 0)
				.map(x -> x * x)
				.sum();
	}

	@Benchmark
	public int megamorphicMaps() {
		return IntPushStream.of(v)
				.map(d -> d * 1)
				.map(d -> d * 2)
				.map(d -> d * 3)
				.map(d -> d * 4)
				.map(d -> d * 5)
				.map(d -> d * 6)
				.map(d -> d * 7)
				.sum();
	}

	@Benchmark
	public int megamorphicFilters() {
		return IntPushStream.of(v)
				.filter(d -> d > 1)
				.filter(d -> d > 2)
				.filter(d -> d > 3)
				.filter(d -> d > 4)
				.filter(d -> d > 5)
				.filter(d -> d > 6)
				.filter(d -> d > 7)
				.sum();
	}

	@Benchmark
	public boolean allMatch() {
		return IntPushStream.of(v).allMatch(x -> x < 10);
	}

	@Benchmark
	public long count() {
		return IntPushStream.of(v).count();
	}

	@Benchmark
	public long filterCount() {
		return IntPushStream.of(v)
				.filter(x -> x % 2 == 0)
				.count();
	}

	@Benchmark
	public long filterMapCount() {
		return IntPushStream.of(v)
				.filter(x -> x % 2 == 0)
				.map(x -> x * x)
				.count();
	}
}
