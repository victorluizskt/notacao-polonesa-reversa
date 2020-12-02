package model.exception;
/*
 *
 * @brief Classe Main
 * @author Victor Luiz Gonçalves & Tássis Fernando
 * @date   02/12/2020
 * @since  02/12/2020
 *
 */
public class ExpressionException extends Exception {
    public ExpressionException() {
        super();
    }

    public ExpressionException(String message) {
        super(message);
    }
}