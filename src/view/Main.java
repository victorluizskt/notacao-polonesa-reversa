package view;

import controller.ExpressaoControle;
import model.exception.ExpressionException;
import model.exception.StackException;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 *
 * @brief Classe Main
 * @author Victor Luiz Gonçalves & Tássis Fernando
 * @date   02/12/2020
 * @since  02/12/2020
 *
 */
public class Main {

//         Expressões do arquivo do trabalho
//         String ler = "1+2";
//         String ler = "1+2-3";
//         String ler = "(1+2)*(3-4)";
//         String ler = "1^2*3-4+5/6/(7+8)";
//         String ler = "3+5*9/(4+3)"
//         String ler = "1+2*3/(4+5)"
//         String ler = "1-2/(3*4^5)";

    private static final Scanner input = new Scanner(System.in);
    private static int choice;
    private static ExpressaoControle expressaoControle = new ExpressaoControle();

    public static void main(String[] args) {
        menuInitial();
    }

    private static void menuInitial() {
        choice = -1;
        while(choice != 0){
            System.out.println("\t\tSeleciona uma opção: ");
            System.out.println("\t[0] Encerrar aplicação.");
            System.out.println("\t[1] Informar uma expressão na forma infixa:");
            try {
                System.out.print("\t=> ");
                choice = input.nextInt();
                switch (choice) {
                    case 0 -> {
                        System.out.println("Encerrando aplicação...");
                        input.close();
                        choice = 0;
                    }
                    case 1 -> {
                        System.out.print("\tInforme a expressão na forma infixa: ");
                        String expInfixa = input.next();
                        String expPos = expressaoControle.transformaPos(expInfixa);
                        Double result = expressaoControle.getResultado(expPos);
                        System.out.println("\tExpressão na forma pós-fixa: " + expPos);
                        System.out.println("\tResultado da expressão: " + result);
                    }
                    default -> System.out.println("\tOpção inválida, escolha uma opção a seguir: ");
                }
            } catch (InputMismatchException ex){
                System.err.println("\tEntrada inválida.");
                choice = 0;
            } catch (IllegalArgumentException | ExpressionException ex){
                System.out.println("\tExpressão inválida." + ex.getMessage());
            } catch (StackException ex){
                System.err.println("\tErro na execução, a expressão informada é incorreta.");
            }
        }
    }
}
