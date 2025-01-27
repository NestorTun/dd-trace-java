package com.datadog.debugger.el.expressions;

import static org.junit.jupiter.api.Assertions.*;

import com.datadog.debugger.el.DSL;
import com.datadog.debugger.el.RefResolverHelper;
import com.datadog.debugger.el.values.StringValue;
import datadog.trace.bootstrap.debugger.el.ValueReferenceResolver;
import datadog.trace.bootstrap.debugger.el.Values;
import org.junit.jupiter.api.Test;

class ContainsExpressionTest {
  private final ValueReferenceResolver resolver = RefResolverHelper.createResolver(this);

  @Test
  void nullExpression() {
    ContainsExpression expression = new ContainsExpression(null, null);
    assertFalse(expression.evaluate(resolver));
    assertEquals("contains(null, null)", expression.prettyPrint());
  }

  @Test
  void undefinedExpression() {
    ContainsExpression expression =
        new ContainsExpression(DSL.value(Values.UNDEFINED_OBJECT), new StringValue(null));
    assertFalse(expression.evaluate(resolver));
    assertEquals("contains(UNDEFINED, \"null\")", expression.prettyPrint());
  }

  @Test
  void stringExpression() {
    ContainsExpression expression =
        new ContainsExpression(DSL.value("abcd"), new StringValue("bc"));
    assertTrue(expression.evaluate(resolver));
    assertEquals("contains(\"abcd\", \"bc\")", expression.prettyPrint());

    expression = new ContainsExpression(DSL.value("abc"), new StringValue("dc"));
    assertFalse(expression.evaluate(resolver));
    assertEquals("contains(\"abc\", \"dc\")", expression.prettyPrint());
  }
}
