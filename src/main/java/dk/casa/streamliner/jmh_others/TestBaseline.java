package dk.casa.streamliner.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
public class TestBaseline extends TestBase {
	@Setup
	public void setUp() {
		super.setUp();
	}

	@Benchmark
	public int sum() {
		int acc = 0;
		for(int i = 0 ; i < v.length ; i++)
			acc += v[i];

		return acc;
	}

	@Benchmark
	public int sumOfSquares() {
		int acc = 0;
		for(int i = 0 ; i < v.length ; i++)
			acc += v[i] * v[i];

		return acc;
	}

	@Benchmark
	public int sumOfSquaresEven() {
		int acc = 0;
		for(int i = 0 ; i < v.length ; i++)
			if (v[i] % 2 == 0)
				acc += v[i] * v[i];

		return acc;
	}

	@Benchmark
	public int megamorphicMaps() {
		int acc = 0;
		for(int i = 0 ; i < v.length ; i++)
			acc += v[i] *1*2*3*4*5*6*7;

		return acc;
	}

	@Benchmark
	public int megamorphicFilters() {
		int acc = 0;
		for(int i = 0 ; i < v.length ; i++)
			if (v[i] > 1 && v[i] > 2 && v[i] > 3 && v[i] > 4 && v[i] > 5 && v[i] > 6 && v[i] > 7)
				acc += v[i];

		return acc;
	}

	@Benchmark
	public boolean allMatch() {
		boolean flag = true;
		for(int i = 0; i < v.length; i++)
			if(v[i] >= 10) {
				flag = false;
				break;
			}
		return flag;
	}

	@Benchmark
	public long count() {
		long c;
		for(c = 0; c < v.length; ++c);
		return c;
	}

	@Benchmark
	public long filterCount() {
		long c = 0;
		for(int i = 0; i < v.length; ++i)
			if(v[i] % 2 == 0)
				++c;

		return c;
	}

	@Benchmark
	public long filterMapCount() {
		long c = 0;
		for(int i = 0; i < v.length; ++i)
			if((v[i] * v[i]) % 2 == 0)
				++c;

		return c;
	}
}
