package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Calculator {
	private static final String CUSTOM_DELIMITER_REGULAR_EXPRESSION = "^//(.)\\n(.*)$";
	private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile(CUSTOM_DELIMITER_REGULAR_EXPRESSION);
	private static final int CUSTOM_DELIMITER_GROUP_INDEX = 1;
	private static final int CUSTOM_DELIMITER_EXPRESSION_INDEX = 2;

	private static final List<String> DEFAULT_DELIMITERS = List.of(",", ":");

	private static final String REGULAR_EXPRESSION_OR = "|";

	private final List<Integer> numbers;

	public Calculator(String expression) {
		Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(expression);

		if (!matcher.matches()) {
			String delimiterRegularExpression = getDelimiterRegularExpression(DEFAULT_DELIMITERS);
			this.numbers = getNumbers(expression, delimiterRegularExpression);
			return;
		}

		String customDelimiter = matcher.group(CUSTOM_DELIMITER_GROUP_INDEX);
		String delimiterRegularExpression = getDelimiterRegularExpression(List.of(customDelimiter));
		String customExpression = matcher.group(CUSTOM_DELIMITER_EXPRESSION_INDEX);
		this.numbers = getNumbers(customExpression, delimiterRegularExpression);
	}

	public int calculate() {
		if (CollectionUtils.isEmpty(this.numbers)) {
			return 0;
		}

		assertAllPositive();
		return getSum();
	}

	private String getDelimiterRegularExpression(List<String> delimiters) {
		return String.join(REGULAR_EXPRESSION_OR, delimiters);
	}

	private List<Integer> getNumbers(String expression, String delimiterRegularExpression) {
		return Arrays.stream(expression.split(delimiterRegularExpression))
			.filter(StringUtils::isNotBlank)
			.map(Integer::parseInt)
			.toList();
	}

	private IntStream getNumbersIntStream() {
		return this.numbers.stream()
			.mapToInt(Integer::intValue);
	}

	private void assertAllPositive() {
		OptionalInt optionalNegativeValue = getNumbersIntStream()
			.filter(value -> value < 0)
			.findAny();

		if (optionalNegativeValue.isPresent()) {
			throw new RuntimeException();
		}
	}

	private int getSum() {
		return getNumbersIntStream()
			.sum();
	}
}
