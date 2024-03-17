package calculator;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {
	@Test
	void calculateWhenIncludingNegative() {
		// given
		String expression = "1,2:3,4:-1";
		Calculator calculator = new Calculator(expression);

		// when
		// then
		assertThrows(RuntimeException.class, () -> calculator.calculate());
	}

	// 놓친 TC
	@Test
	void calculateWhenEmpty() {
		// given
		String expression = "";
		Calculator calculator = new Calculator(expression);

		// when
		int result = calculator.calculate();

		// then
		assertThat(result).isEqualTo(0);
	}

	// 놓친 TC
	@Test
	void calculateWhenOnlyOneNumber() {
		// given
		String expression = "1";
		Calculator calculator = new Calculator(expression);

		// when
		int result = calculator.calculate();

		// then
		assertThat(result).isEqualTo(1);
	}

	@Test
	void calculateWhenDefaultDelimiter() {
		// given
		String expression = "1,2:3,4";
		Calculator calculator = new Calculator(expression);

		// when
		int result = calculator.calculate();

		// then
		assertThat(result).isEqualTo(10);
	}

	@Test
	void calculateWhenCustomDelimiter() {
		// given
		String expression = "///\n1/2/3/4";
		Calculator calculator = new Calculator(expression);

		// when
		int result = calculator.calculate();

		// then
		assertThat(result).isEqualTo(10);
	}
}