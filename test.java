import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeMap;

class Main {
    public static void main(String[] args) {
        

        System.out.println("Введите математическое вырфжение из 2х чисел не более от 1 до 10 каждое:");        

        Scanner inputExsp = new Scanner(System.in);
        String mathExspression = inputExsp.nextLine();
        
        String result = calc(mathExspression);

        System.out.println(result);
        
    }
        
    public static String calc(String input) {
        String[] scrMathAct = {"\\+", "-", "/","\\*"};

        FindMathAct mathInExsp = new FindMathAct();
        mathInExsp.exspression = input;
        int act = mathInExsp.defFindMathAct();
        
        // Проверка содержания мат. знаков в выражении
        if(act == - 1){
            return "Выражение не содержит математических знаков";
        }

        // Проверка колличества математических знаков
        String[] data = input.split(scrMathAct[act]);
        if(data.length>2){
            return "Введено некорректное выражение";
        }
        FindMathAct mathInExspData = new FindMathAct();
        mathInExspData.exspression = data[1];
        int actInData = mathInExspData.defFindMathAct();
        if(actInData != -1){
            return "Введено некорректное выражение";
        }

        // Проверка арабских чисел
        IsRoman dataA = new IsRoman();
        IsRoman dataB = new IsRoman();
        dataA.data = data[0];
        dataB.data = data[1];
        int dataIsRomanA = dataA.FindeRoman();
        int dataIsRomanB = dataB.FindeRoman();
        
        
        // Проверка одинакового типа чисел
        if(dataIsRomanA == dataIsRomanB){


            // Если числа римские
            if(dataIsRomanA == 1){                
                int dataConvertRomanA = dataA.ConvertRomanToArabian();
                int dataConvertRomanB = dataB.ConvertRomanToArabian();
                //Проверка на отрицательный результать
                if (dataConvertRomanA <= dataConvertRomanB && scrMathAct[act] == "-"){
                    return ("В римской системе нет отрицательных чисел и 0");
                }
                //Проверка, каждое число не более 10
                if (dataConvertRomanA > 10 || dataConvertRomanB > 10){
                    return ("Числа должны быть не более 10");
                }

                CalculateExp arabian = new CalculateExp();
                arabian.a = dataConvertRomanA;                
                arabian.b = dataConvertRomanB;                
                arabian.act = act;
                int resulsExp = arabian.calculateResult();
                System.out.println(resulsExp);

                ConvertArabianToRoman romanResult = new ConvertArabianToRoman();
                romanResult.rowData = resulsExp;
                String result = romanResult.getConvertation();
                return result;
            }


            // Если числа арабские
            else{
                
                CalculateExp arabian = new CalculateExp();
                arabian.a = Integer.parseInt(data[0]);
                arabian.b = Integer.parseInt(data[1]);
                arabian.act = act;
                //Проверка деления на 0
                if (Integer.parseInt(data[1])==0 && act == 2){
                    return "Введено некорректное выражение";
                }
                //Проверка, каждое число не больше 10
                if (Integer.parseInt(data[1])>10 || Integer.parseInt(data[0]) > 10){
                    return "Введено некорректное выражение";
                }
                int result = arabian.calculateResult();
                String strResult = Integer.toString(result);
                return strResult;
            }
            
        }
        else{
            return "Введено некорректное выражение";
        }
    }    
}

// Находим знак в математическом выражении:
class FindMathAct{
    String exspression;

    int defFindMathAct(){
        String[] mathAct = {"+", "-", "/","*"};        

        int mathActIndex = -1;

        for (int i = 0; i < mathAct.length; i++){
            if (exspression.contains(mathAct[i])){
                mathActIndex = i;
                break;
            }
        }               
        return mathActIndex;       
    }
    
}

class IsRoman{
    String data; 
    
    HashMap<Character, Integer> romanKeyMap = new HashMap<>();
    

    int FindeRoman(){        
        romanKeyMap.put('I', 1);        
        romanKeyMap.put('V', 5);        
        romanKeyMap.put('X', 10);        
        
        char[] dataArr = data.toCharArray();
        int findRoman = 1;
        int testRoman = 0;      

        for(int i = 0; i < data.length(); i++){            
            try{
                testRoman = romanKeyMap.get(dataArr[i]);                
            }
            catch(Exception e){                
                findRoman = -1;
                return findRoman;
            }
        }
        return findRoman;        
    }
    int ConvertRomanToArabian(){
        romanKeyMap.put('I', 1);        
        romanKeyMap.put('V', 5);        
        romanKeyMap.put('X', 10);


        int endIndex = data.length()-1;
        char[] dataArr = data.toCharArray();
        int arabian;

        int result = romanKeyMap.get(dataArr[endIndex]);

        for (int i = endIndex-1; i >= 0; i--){
            arabian = romanKeyMap.get(dataArr[i]);

            if (arabian < romanKeyMap.get(dataArr[i+1])){
                result -= arabian;
            }
            else {
                result += arabian;
            }
        }
        return result;
    }
} 

class CalculateExp {
    int a;
    int b;
    int act;

    int calculateResult(){
        String[] mathAct = {"+", "-", "/", "*"};

        if (mathAct[act] == "+"){
            int result = a + b;            
            return result;
        }
        if (mathAct[act] == "-"){
            int result = a - b;
            return result;
        }
        if (mathAct[act] == "/"){
            int result = a / b;
            return result;
        }
        if (mathAct[act] == "*"){
            int result = a * b;            
            return result;
        }
        return 0;
    }
}

class ConvertArabianToRoman{
    int rowData;
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    String getConvertation(){
        arabianKeyMap.put(100, "C");        
        arabianKeyMap.put(90, "XC");        
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");         

        int arabianKey;
        String romanKey = "";

        do {
            arabianKey = arabianKeyMap.floorKey(rowData);
            romanKey += arabianKeyMap.get(arabianKey);
            rowData -= arabianKey;
        }
        while (rowData != 0);
        
        return romanKey;
    }    
}