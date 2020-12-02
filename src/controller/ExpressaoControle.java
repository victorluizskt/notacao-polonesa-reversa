package controller;

import model.PilhaDinamica;
import model.exception.ExpressionException;
import model.exception.StackException;

/*
 *
 * @brief Classe Main
 * @author Victor Luiz Gonçalves & Tássis Fernando
 * @date   02/12/2020
 * @since  02/12/2020
 *
 */
public class ExpressaoControle {

    private PilhaDinamica<Character> pilhaExp = new PilhaDinamica<>();

    public String transformaPos(String expInfixa) throws IllegalArgumentException, StackException, ExpressionException {
        String expPos = "";
        int isAlgAnt = 0;

        if (expInfixa != null && expInfixa.length() > 0) {
            for (int i = 0; i < expInfixa.length(); i++) {
                char c = expInfixa.charAt(i);

                /*
                Verifica dois algarismos em seguida.
                 */
                if (isNumber(c + "")) {
                    isAlgAnt++;
                } else {
                    isAlgAnt = 0;
                }

                if (isAlgAnt > 1) {
                    throw new ExpressionException("A expressão informada está num formato inválido.");
                }

                if (isNumber(c + "")) {
                    expPos += c;
                } else if (c == '(') {
                    pilhaExp.push(c);
                } else if (c == ')') {
                    char current = ' ';
                    while (!pilhaExp.isEmpty()) {
                        current = pilhaExp.pop();
                        if (current == '(') {
                            break;
                        } else if (isOperador(current)) {
                            expPos += current;
                        } else {
                            throw new ExpressionException("A expressão informada está num formato inválido.");
                        }
                    }
                    if (current != '(') {
                        throw new ExpressionException("A expressão informada está num formato inválido.");
                    }
                } else if (isOperador(c)) {
                    if (pilhaExp.isEmpty() || (pilhaExp.getTopo() != null && getPrioridade(c) > getPrioridade(pilhaExp.getTopo()))) {
                        pilhaExp.push(c);
                    } else {
                        char op = pilhaExp.pop();
                        expPos += op;
                        pilhaExp.push(c);
                    }
                }
            }

            while (!pilhaExp.isEmpty()) {
                char current = pilhaExp.pop();
                if (current != '(' && current != ')') {
                    expPos += current;
                } else {
                    throw new ExpressionException("A expressão informada está num formato inválido.");
                }
            }
            return expPos;
        } else {
            throw new IllegalArgumentException("A expressão passada é nula, portanto não pode ser avaliada.");
        }
    }

    private boolean isNumber(String token) {
        return token.matches("^[0-9]*$");
    }

    private boolean isOperador(char token) {
        return token == '+' || token == '-' || token == '*' || token == '/' || token == '^' || token == '(' || token == ')';
    }

    private int getPrioridade(char token) {
        return switch (token) {
            case '+' -> 1;
            case '-' -> 1;
            case '*' -> 2;
            case '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }

    public Double getResultado(String expPos) throws StackException, ExpressionException {
        PilhaDinamica<Double> pilhaNum = new PilhaDinamica<>();

        for (int i = 0; i < expPos.length(); i++) {
            char c = expPos.charAt(i);

            if (isNumber(c + "")) {
                pilhaNum.push(Double.parseDouble(c + ""));
            } else if (isOperador(c)) {
                Double num2 = pilhaNum.pop();
                Double num1 = pilhaNum.pop();
                Double result = getOperacao(num1, num2, c);
                pilhaNum.push(result);
            }
        }
        Double result = pilhaNum.getTopo();
        return result;
    }

    private Double getOperacao(Double num1, Double num2, char op) throws ExpressionException {
        return switch (op) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            case '^' -> Math.pow(num1, num2);
            default -> throw new ExpressionException("Erro ao avaliar a expressão. Foi encontrada uma operação inválida.");
        };
    }
}
