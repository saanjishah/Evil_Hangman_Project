/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman.project;

/**
 *
 * @author shah1932
 */
public class Points {
    private int length, score;
    public Points(int wordLen){
        length = wordLen;
    }
    public int getLetScore(String usedLet){
        score = 0;
        for(int i = 0; i<usedLet.length();i++){
            if(usedLet.length()>2 && (usedLet.substring(i).equals("Q") || usedLet.substring(i).equals("U"))){
                for(int j = i+1 ; j < usedLet.length();j++){
                    if((usedLet.substring(j).equals("Q") || usedLet.substring(j).equals("U"))){
                        score = score + 500;
                    }
                }
            }
            String let = usedLet.substring(i,i+1);
            score = score + calculate(let);
        }
        return score;
    }
    public int calculate(String letter){
        if(length<6 && (letter.equals("A")||letter.equals("E")||letter.equals("I")||letter.equals("O")||letter.equals("U"))){
            return 100;
        }
        else if(length<6 && (letter.equals("S")||letter.equals("T")||letter.equals("P")||letter.equals("R")||letter.equals("N")||letter.equals("Y"))){
            return 300;
        }
        else if(length>6 && (letter.equals("Z")||letter.equals("X")||letter.equals("W")||letter.equals("K")||letter.equals("Q")||letter.equals("V"))){
            return 800;
        }
        else if(length>6){
            return 400;
        }else{
            return 200;
        }
    }
}
