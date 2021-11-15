import java.util.ArrayDeque;
import java.util.Scanner;
// “I certify that the codes/answers of this assignment are entirely my own work.”
// Benjamin Sarol
public class CFG{
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        CFG pleaseWork = new CFG();
        System.out.println("Please give input: ");
        while(sc.hasNextLine()){
            try {
                int value = pleaseWork.statement(sc.nextLine());
                System.out.println("Derivation Found, No. of Derivation Steps: " + pleaseWork.getState()[0] + ", Value: " + value);
            } catch(Exception e) {
                System.out.println("No Derivation Found");
            }
            System.out.println("Please give input: ");
        }
    }


    public int[] state;
    public CFG(){
        state = new int[2];
        state[0] = 0;
        state[1] = 1;
    }
    public int[] getState(){
        return state;
    }
    public int statement(String n){
        state[0] = 0;
        state[0]++;
        return exp(n);
    }
    public int exp(String n){
        state[0]++;
        ArrayDeque<String> stack = new ArrayDeque<>();
        StringBuilder t = new StringBuilder();
        Scanner sc = new Scanner(n);
        sc.useDelimiter("");
        while(sc.hasNext()){
            stack.push(sc.next());
        }
        boolean isInside = false;
        while(!stack.isEmpty() && !stack.peek().equals("+") && !stack.peek().equals("-")){
            String tempo = stack.pop();
            if(tempo.equals(")")){
                t.insert(0, tempo);
                while(!stack.isEmpty() && !stack.peek().equals("(")){
                    t.insert(0, stack.pop());
                }
                t.insert(0, stack.pop());
            }else{
                t.insert(0, tempo);
            }
        }
        if(stack.isEmpty()){
            return term(t.toString());
        }
        StringBuilder exp = new StringBuilder();
        if(stack.peek().equals("+")){
            stack.pop();
            while(!stack.isEmpty()){
                exp.insert(0, stack.pop());
            }
            return exp(exp.toString()) + term(t.toString());
        }
        if(stack.peek().equals("-")){
            stack.pop();
            while(!stack.isEmpty()){
                exp.insert(0, stack.pop());
            }
            return exp(exp.toString()) - term(t.toString());
        }
        state[1] = -1;
        return (Integer) null;
    }
    public int term(String n){
        state[0]++;
        ArrayDeque<String> stack = new ArrayDeque<>();
        StringBuilder p = new StringBuilder();
        Scanner sc = new Scanner(n);
        sc.useDelimiter("");
        while(sc.hasNext()){
            stack.push(sc.next());
        }
        boolean oneStar = false;
        while(!stack.isEmpty() && !stack.peek().equals("/") && !oneStar){
            String tempo = stack.pop();
            if(tempo.equals("*")){
                if(stack.peek().equals("*")){
                    p.insert(0, tempo);
                    p.insert(0, stack.pop());
                }else{
                    oneStar = true;
                }
            }else{
                p.insert(0, tempo);
            }
        }
        if(stack.isEmpty()){
            return power(p.toString());
        }
        
        StringBuilder term = new StringBuilder();
        if(oneStar){
            while(!stack.isEmpty()){
                term.insert(0, stack.pop());
            }
            return term(term.toString()) * power(p.toString());
        }
        if(stack.peek().equals("/")){
            stack.pop();
            while(!stack.isEmpty()){
                term.insert(0, stack.pop());
            }
            return term(term.toString()) / power(p.toString());
        }
        state[1] = -1;
        return (Integer) null;
    }
    public int power(String n){
        state[0]++;
        ArrayDeque<String> stack = new ArrayDeque<>();
        StringBuilder f = new StringBuilder();
        Scanner sc = new Scanner(n);
        sc.useDelimiter("");
        while(sc.hasNext()){
            stack.push(sc.next());
        }
        boolean twoStar = false;
        while(!stack.isEmpty() && !twoStar){
            String tempo = stack.pop();
            if(tempo.equals("*")){
                if(!stack.isEmpty() && stack.peek().equals("*")){
                    stack.pop();
                    twoStar = true;
                }else{
                    f.insert(0, tempo);
                }
            }else{
                f.insert(0, tempo);
            }
        }
        if(stack.isEmpty()){
            return factor(f.toString());
        }
        StringBuilder power = new StringBuilder();
        if(twoStar){
            while(!stack.isEmpty()){
                power.insert(0, stack.pop());
            }
            return (int) Math.pow(factor(power.toString()), power(f.toString()));
        }
        state[1] = -1;
        return (Integer) null;
    }
    public int factor(String n){
        state[0]++;
        if(n.length() <= 1){
            return number(n);
        }
        else{
            ArrayDeque<String> stack = new ArrayDeque<>();
            StringBuilder exp = new StringBuilder();
            Scanner sc = new Scanner(n);
            sc.useDelimiter("");
            while(sc.hasNext()){
                stack.push(sc.next());
            }
            if(stack.peek().equals(")")){
                stack.pop();
                while(!stack.isEmpty() && !stack.peek().equals("(")){
                    String tempo = stack.pop();
                    exp.insert(0, tempo);
                }
                if(exp.charAt(0) == '-'){
                    state[0]-=5;
                    exp.insert(0, "0");
                }
                return exp(exp.toString());
            }
        }
        state[1] = -1;
        return (Integer) null;
    }
    public int number(String n){
        state[0]++;
        switch(n){
            case "0": return 0;
            case "1": return 1;
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            default: return (Integer) null;
        }

    }
}