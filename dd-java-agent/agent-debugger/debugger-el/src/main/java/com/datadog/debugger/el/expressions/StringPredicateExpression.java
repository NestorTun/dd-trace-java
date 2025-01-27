package com.datadog.debugger.el.expressions;

import static com.datadog.debugger.el.Expression.nullSafePrettyPrint;

import com.datadog.debugger.el.Value;
import com.datadog.debugger.el.values.StringValue;
import datadog.trace.bootstrap.debugger.el.ValueReferenceResolver;
import java.util.function.BiPredicate;

public class StringPredicateExpression implements BooleanExpression {
  private final ValueExpression<?> sourceString;
  private final StringValue str;
  private final BiPredicate<String, String> predicate;
  private final String name;

  public StringPredicateExpression(
      ValueExpression<?> sourceString,
      StringValue str,
      BiPredicate<String, String> predicate,
      String name) {
    this.sourceString = sourceString;
    this.str = str;
    this.predicate = predicate;
    this.name = name;
  }

  @Override
  public Boolean evaluate(ValueReferenceResolver valueRefResolver) {
    Value<?> sourceValue =
        sourceString != null ? sourceString.evaluate(valueRefResolver) : Value.nullValue();
    if (sourceValue.getValue() instanceof String) {
      String sourceStr = (String) sourceValue.getValue();
      return predicate.test(sourceStr, str.getValue());
    }
    return Boolean.FALSE;
  }

  @Override
  public String prettyPrint() {
    return name + "(" + nullSafePrettyPrint(sourceString) + ", " + nullSafePrettyPrint(str) + ")";
  }
}
