import net.objecthunter.exp4j.Expression;
import ru.vad1mchk.math.montecarlo.core.util.ExpressionParser;

public class Test {
    public static void main(String[] args) {
        Expression expression = ExpressionParser.INSTANCE.parse("sin(x)-sin(y)", "x", "y");
        expression.setVariable("x", Math.PI/2);
        expression.setVariable("y", -Math.PI/2);
        System.out.println(expression.evaluate());
    }
}
