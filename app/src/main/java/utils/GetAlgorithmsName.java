package utils;

public class GetAlgorithmsName {
    public static String get(int algorithmOfSorting){
        if(algorithmOfSorting == 1){
            return "Less seen question";
        }
        else if(algorithmOfSorting == 2){
            return "More seen question";
        }
        else if(algorithmOfSorting == 3){
            return "Most wrong answer";
        }
        return null;
    }
}
