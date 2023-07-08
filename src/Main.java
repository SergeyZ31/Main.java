import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(calc(input));
    }

    public static String calc(String input) throws Exception {

        String ans = "";
        StringBuilder ansInArabic = new StringBuilder();
        int a = 0;
        int b = 0;
        boolean isArabicNumA = false;
        boolean isArabicNumB = false;
        String[] strAr = input.split(" ");


        if (strAr.length > 3) {
            throw new Exception("т.к. формат математической операции не удовлетворяет" +
                    " заданию - два операнда и один оператор (+, -, /, *)");
        }
        if (strAr[0].length() > 4) {
            throw new Exception("т.к. введен не верный формат данных");
        }
        if (strAr.length < 3) {
            throw new Exception("т.к. строка не является математической операцией");
        }

        if (strAr[2].length() > 4) {
            throw new Exception("т.к. введен не верный формат данных");
        }

        if (!(strAr[1].contains("+")) && !(strAr[1].contains("-")) && !(strAr[1].contains("*")) && !(strAr[1].contains("/"))) {
            throw new Exception("т.к. нет подходящей математической операции");
        }

        String oper = strAr[1];

        for (ArabicNum ar : ArabicNum.values()) {
            if (ar.name().equals(strAr[0])) {
                isArabicNumA = true;
                break;
            }
        }
        for (ArabicNum ar : ArabicNum.values()) {
            if (ar.name().equals(strAr[2])) {
                isArabicNumB = true;
                break;
            }
        }

        if (!isArabicNumA && isArabicNumB) {
            throw new Exception("т.к. используются одновременно разные системы счисления");
        }

        if (!isArabicNumB) {
            try {
                b = Integer.parseInt(strAr[2]);
                if (b > 10 ||  b < 1) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new Exception("т.к. введен не верный формат чисел");
            }
        }

        if (!isArabicNumA) {
            try {
                a = Integer.parseInt(strAr[0]);
                if (a > 10 || a < 1) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new Exception("т.к. введен не верный формат чисел");
            }
        }


        if (isArabicNumA && isArabicNumB) {

            ArabicNum arabicNum1 = ArabicNum.valueOf(strAr[0]);
            ArabicNum arabicNum2 = ArabicNum.valueOf(strAr[2]);
            a = arabicNum1.getValue();
            b = arabicNum2.getValue();
            ans = operating(ans, oper, a, b);
            if (Integer.parseInt(ans) < 1) {
                throw new Exception("т.к. в римской системе исчисления нет отрицательных чисел");
            }
            if (Integer.parseInt(ans) == 0) {
                throw new Exception("т.к. в римской системе исчисления нет ноля");
            }
            if (Integer.parseInt(ans) <= 10) {
                for (ArabicNum ar : ArabicNum.values()) {
                    if (ar.getValue() == Integer.parseInt(ans)) {
                        ansInArabic.append(ar.name());
                        return ansInArabic.toString();
                    }
                }
            }
            if (Integer.parseInt(ans) > 10 && Integer.parseInt(ans) <= 19) {
                ansInArabic.append("X");
                for (ArabicNum ar : ArabicNum.values()) {
                    if (ar.getValue() == Integer.parseInt(String.valueOf(ans.charAt(1)))) {
                        ansInArabic.append(ar.name());
                        return ansInArabic.toString();
                    }
                }
            }
            if (Integer.parseInt(ans) > 19 && Integer.parseInt(ans) < 50) {
                int ten = Integer.parseInt(ans) / 10;
                int lastNum = Integer.parseInt(ans) % 10;
                if(ten == 4){
                    ansInArabic.append("X");
                    ansInArabic.append("L");
                    for (ArabicNum ar : ArabicNum.values()) {
                        if (ar.getValue() == lastNum) {
                            ansInArabic.append(ar.name());
                            return ansInArabic.toString();
                        }
                    }
                }else {
                    ansInArabic.append("X".repeat(Math.max(0, ten)));
                    for (ArabicNum ar : ArabicNum.values()) {
                        if (ar.getValue() == lastNum) {
                            ansInArabic.append(ar.name());
                            return ansInArabic.toString();
                        }
                    }
                }

            }
            if (Integer.parseInt(ans) > 51 && Integer.parseInt(ans) < 90) {
                ansInArabic.append("L");
                int ten = (Integer.parseInt(ans) - 50) / 10;
                int lastNum = Integer.parseInt(ans) % 10;
                if(ten != 0){
                    ansInArabic.append("X".repeat(Math.max(0, ten)));
                }
                if (lastNum != 0) {
                    for (ArabicNum ar : ArabicNum.values()) {
                        if (ar.getValue() == lastNum) {
                            ansInArabic.append(ar.name());
                            return ansInArabic.toString();
                        }
                    }
                }return ansInArabic.toString();
            }
            if (Integer.parseInt(ans) == 50) {
                ansInArabic.append("L");
                return ansInArabic.toString();
            }

            if (Integer.parseInt(ans) == 90) {
                ansInArabic.append("XC");
                return ansInArabic.toString();
            }
            if (Integer.parseInt(ans) == 100) {
                ansInArabic.append("C");
                return ansInArabic.toString();
            }
            return ansInArabic.toString();
        } else {
            ans = operating(ans, oper, a, b);
        }
        return ans;
    }

    static String operating(String ans, String oper, int a, int b) {
        switch (oper) {
            case "+" -> ans = String.valueOf(a + b);
            case "-" -> ans = String.valueOf(a - b);
            case "*" -> ans = String.valueOf(a * b);
            case "/" -> ans = String.valueOf(a / b);
        }
        return ans;
    }
}

enum ArabicNum {
    I(1), II(2), III(3), IV(4), V(5), VI(6),
    VII(7), VIII(8), IX(9), X(10);
    private final int value;

    ArabicNum(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}