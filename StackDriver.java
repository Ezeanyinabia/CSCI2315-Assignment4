import java.util.Scanner;

public class StackDriver
{
    public static void main(String []args)
    {
        Scanner in = new Scanner(System.in);
        String currentExpr = null;
        String postfixExpr = null;
        boolean hasExpression = false;
        boolean evaluated = false;
        boolean converted = false;

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Enter a fully parenthesized expression.");
            System.out.println("2. Evaluate a fully parenthesized expression.");
            System.out.println("3. Convert a fully parenthesized expression to post-fix.");
            System.out.println("4. Evaluate post-fix");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice;
            if (in.hasNextInt()) {
                choice = in.nextInt();
                in.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                in.next(); // consume invalid token
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter any parenthesized expression: ");
                    String s = in.nextLine();
                    
                    // check to see if it is a legal parenthesization
                    if (isLegal(s)) {
                        System.out.println(s + " is a legal parenthesization");
                        currentExpr = s;
                        hasExpression = true;
                        evaluated = false;
                        converted = false;
                        postfixExpr = null;
                    } else {
                        System.out.println(s + " is not a legal parenthesization");
                    }
                    break;

                case 2:
                    if (!hasExpression) {
                        System.out.println("Error: No expression has been entered.");[cite: 1]
                    } else if (evaluated) {
                        System.out.println("Error: This expression has already been evaluated. Please enter a new expression using option 1.");[cite: 1]
                    } else {
                        evaluateExpr(currentExpr);
                        evaluated = true;
                    }
                    break;

                case 3:
                    if (!hasExpression) {
                        System.out.println("Error: No expression has been entered.");[cite: 1]
                    } else if (converted) {
                        System.out.println("Error: This expression has already been converted. Please enter a new expression using option 1.");[cite: 1]
                    } else {
                        postfixExpr = convertToPostfix(currentExpr);
                        System.out.println("The post-fix notation is: " + postfixExpr);[cite: 1]
                        converted = true;
                    }
                    break;

                case 4:
                    System.out.print("Would you like to (1) use the post-fix expression from part c, or (2) enter a new post-fix expression? Enter 1 or 2: ");[cite: 1]
                    int subChoice;
                    if (in.hasNextInt()) {
                        subChoice = in.nextInt();
                        in.nextLine();
                    } else {
                        System.out.println("Invalid input.");
                        in.next();
                        break;
                    }

                    if (subChoice == 1) {
                        if (!converted || postfixExpr == null) {
                            System.out.println("Error: No post-fix expression is available from part c.");[cite: 1]
                        } else {
                            System.out.println("Evaluating converted post-fix expression: " + postfixExpr);
                            evaluatePostfix(postfixExpr);
                        }
                    } else if (subChoice == 2) {
                        System.out.print("Enter a new post-fix expression: ");[cite: 1]
                        String newPostfix = in.nextLine();
                        evaluatePostfix(newPostfix);
                    } else {
                        System.out.println("Invalid selection.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    in.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select between 1 and 5.");
            }
        }
    }

    // Helper method to convert a fully parenthesized expression to postfix notation
    public static String convertToPostfix(String str) {
        StringBuilder postfix = new StringBuilder();
        ListStack<Character> s2 = new ListStack<Character>();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
                s2.push(c);
            } else if (c == ')') {
                if (!s2.isEmpty()) {
                    postfix.append(s2.topAndPop());
                }
            }
        }
        return postfix.toString();
    }

    // Helper method to evaluate a postfix expression
    public static void evaluatePostfix(String str) {
        ListStack<Integer> s1 = new ListStack<Integer>();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                s1.push((int)(c - '0'));
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
                int opnd2 = s1.topAndPop();
                int opnd1 = s1.topAndPop();

                switch (c) {
                    case '+':
                        s1.push(opnd1 + opnd2);
                        break;
                    case '-':
                        s1.push(opnd1 - opnd2);
                        break;
                    case '*':
                        s1.push(opnd1 * opnd2);
                        break;
                    case '/':
                        s1.push(opnd1 / opnd2);
                        break;
                    case '%':
                        s1.push(opnd1 % opnd2);
                        break;
                }
            }
        }

        if (!s1.isEmpty()) {
            System.out.println("The result of the post-fix expression is: " + s1.topAndPop());
        } else {
            System.out.println("Error evaluating post-fix expression.");
        }
    }

    // go thru the string and
    // every time it sees a ( it pushes it on the stack, and it will only pop a ( off the stack
    // when it encounteres a ).
    public static boolean isLegal(String str)
    {
	int i;
	ListStack<Character> s = new ListStack<Character>();

	for(i = 0; i < str.length(); i++)
	    if(str.charAt(i) == '(' )
		s.push('(');
	    else if(str.charAt(i) == ')')
		s.pop();

	return s.isEmpty() && i == str.length();
    }

    // Evaluate the expression, but the expression must be fully parenthesized
    public static void evaluateExpr(String str)
    {
	int i;
	ListStack<Integer> s1 = new ListStack<Integer>();
	ListStack<Character> s2 = new ListStack<Character>();

	for(i = 0; i < str.length(); i++){
	    
	    if(Character.isDigit(str.charAt(i)))
		s1.push((int)(str.charAt(i)- '0'));
	    else if(str.charAt(i) == '+' || str.charAt(i) == '-' ||
		    str.charAt(i) == '*' || str.charAt(i) == '/' ||
		    str.charAt(i) == '%')
		s2.push(str.charAt(i));
	    else if(str.charAt(i) == ')'){
		
		int opnd1, opnd2;
		char oper = s2.topAndPop();
		opnd2 = s1.topAndPop();
		opnd1 = s1.topAndPop();
		
		switch(oper){
		case '+': 
		    s1.push(opnd1 + opnd2);
		    break;
		case '-': 
		    s1.push(opnd1 - opnd2);
		    break;
		case '*': 
		    s1.push(opnd1 * opnd2);
		    break;
		case '/': 
		    s1.push(opnd1 / opnd2);
		    break;
		case '%': 
		    s1.push(opnd1 % opnd2);
		    break;
		}

	    }
	    
	}

	System.out.println("The result of the expression is: " + s1.topAndPop());

    }
}
