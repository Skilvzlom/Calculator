import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {
    // метод для проверки ялвяется ли строка числом
    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    //Метод для перевода из римских чисел в арабские
    public static int romanToDigit(String roman){
        HashMap<Character, Integer> m = new HashMap<>();

        m.put('I', 1);
        m.put('V', 5);
        m.put('X', 10);
        m.put('L', 50);
        m.put('C', 100);

        int ans = 0;

        for (int i = 0; i < roman.length(); i++) {
            if (i < roman.length() - 1 && m.get(roman.charAt(i)) < m.get(roman.charAt(i + 1))) {
                ans -= m.get(roman.charAt(i));
            } else {
                ans += m.get(roman.charAt(i));
            }
        }

        return ans;
    }


// перевод их арабских в римские
    private static String digitToRoman(int digit){
        int[] values = {100,90,50,40,10,9,5,4,1};

        String[] romanLiterals = {"C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder roman = new StringBuilder();
        for(int i=0;i<values.length;i++) {
            while(digit >= values[i]) {
                digit -= values[i];
                roman.append(romanLiterals[i]);
            }
        }
        return roman.toString();
    }


    public static String calc(String input) {
        if (input.matches("[0-9IVX ]+[+\\-*/][ 0-9IVX]+")){ // Проверяем подходит ли введеная строка условию задачи

            String temp = input.replaceAll(" ", ""); // Убираем пробелы из выражения
            String[] parts = temp.split("[.+-.*/]"); // Обрезаем строку на левую и правую части

            char symb = temp.charAt(temp.indexOf(parts[0]) + parts[0].length()); // находим знак операции

            boolean flagFirst = isDigit(parts[0].strip()), flagSecond = isDigit(parts[1].strip()); //Проверяем числа ли наши левая и правая части

            int firstNum = flagFirst ? Integer.valueOf(parts[0]):romanToDigit(parts[0]); // Переводим левую часть в число
            int secondNum = flagSecond ? Integer.valueOf(parts[1]):romanToDigit(parts[1]); // переводим правую часть в число

            int finalNum = 0;
            switch (symb) {
                case '+' -> finalNum = firstNum + secondNum;
                case '/' -> finalNum = firstNum / secondNum;
                case '*' -> finalNum = firstNum * secondNum;
                case '-' -> finalNum = firstNum - secondNum;
            }

            if (firstNum > 10 || secondNum > 10){
                return "throws Exception";
            }

            if (!flagFirst && !flagSecond && finalNum <= 0){ // Проверка для римских чисел
                return "throws Exception";
            }
            if (flagFirst && !flagSecond || !flagFirst && flagSecond){ // Проверка если одно число арабское, а другое римское
                return "throws Exception";
            }
            if (!flagFirst){
                return digitToRoman(finalNum);
            }
            return String.valueOf(finalNum);


        }
        return "throws Exception";
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String result = calc(in.nextLine());
        System.out.print(result);
    }
}