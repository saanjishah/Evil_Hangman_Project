/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evil.hangman.project;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 *
 * @author Saanji Shah
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label, guessedLet, incLab, corLab, result;
    private String word = "", displayBegin = "", guess, display = "", usedLet = "", name = "";
    private int count,corGuess =0, incGuess = 0, length, score;
    private boolean chosen=false;
    Dictionary dictClass = new Dictionary();
    Points pointClass;
    String[] picts = new String[14];
    List<String> possWords = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<Integer>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> finList = new ArrayList<String>();
    private ObservableList displayList = FXCollections.observableArrayList();
    @FXML 
    private ImageView pics;
    @FXML
    private ListView topFive;
    public FXMLDocumentController() {
    }
    private int numPic=0;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        name = JOptionPane.showInputDialog("What is your name?");
        names.add(name);
//        try{
//            FileReader reader = new FileReader("src/resources/file.txt");
//            Scanner in = new Scanner(reader);
//            while(in.hasNextLine())
//            {
//                String temp = in.nextLine();
//                finList.add(temp);
////                scores.add(temp);
//                displayList.add(temp);
//                topFive.setItems(displayList);
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println("SOMETHING HAS GONE HORRIBLY WRONG WE'RE ALL GONNA DIE!");
//        }
        length= (int) Math.floor(Math.random()*6)+4;
        pointClass = new Points(length);
        possWords = dictClass.getArrayNoMatch(usedLet, length);
        numPic =0;
        picts[0]=("resources/Start.PNG");
        picts[1] =("resources/Head.PNG");
        picts[2]=("resources/Body.PNG");
        picts[3]=("resources/One arm.PNG");
        picts[4]=("resources/Two Arm.PNG");
        picts[5]=("resources/One leg.PNG");
        picts[6]=("resources/Two leg.PNG");
        picts[7]=("resources/One eye.PNG");
        picts[8]=("resources/Two eyes.PNG");
        picts[9]=("resources/Mouth.PNG");
        picts[10]=("resources/Shirt.PNG");
        picts[11]=("resources/Pant.PNG");
        picts[12]=("resources/Hat.PNG");
        picts[13]=("resources/Tie.PNG");
        picts[13]=("resources/End.PNG");
        display();
    }
    private void display(){
        for(int i=0;i<length;i++){
            displayBegin = displayBegin + " " + "_";
        }
        label.setText(displayBegin);
        setPics();
    }
    @FXML
    private void setPics(){
        pics.setImage(new Image(picts[numPic] + ""));
        numPic++;
        incLab.setText(Integer.toString(incGuess));
        corLab.setText(Integer.toString(corGuess));
    }
    @FXML
    private void getGuess(){
        result.setText("");
        guess = JOptionPane.showInputDialog("Guess a letter");
        guess = guess.toUpperCase();
        usedLet = usedLet+guess;
        guessedLet.setText(usedLet);
        int whichWord;
        if(chosen==false){
            if(incGuess==12){
                System.out.println("YOU LOSE");
                endGame();
            }else{
               incGuess++; 
            }   
            setPics();
            possWords = dictClass.getArrayNoMatch(usedLet, length);
            if(possWords.size()==1){
                word = possWords.get(0);
                System.out.println(word);
                chosen=true;
            }else if(possWords.size()==0){
                possWords = dictClass.getArrayNoMatch((usedLet.substring(0,(usedLet.length()-1))), length);
                whichWord=(int) Math.floor(Math.random()*(usedLet.length()-2));
                word = possWords.get(whichWord);
                System.out.println(word);
                chosen=true;
            }
        }
        if(chosen==true){
           checkGuess(); 
        }
    }
    private void checkGuess(){
        display = "";
        boolean match = false;
        boolean search = true;
        String temp;
        for(int i = 0; i< usedLet.length()-1;i++){
            if(guess.equals(usedLet.substring(i,i+1))){
                search = false;
                label.setText(displayBegin);
            }   
        }
        if(search){
            for(int i=0;i<word.length();i++){
                match = false;
                temp = "";
                for(int j =0;j<usedLet.length();j++){
                    if(usedLet.substring(j,j+1).equals(word.substring(i, i+1))){
                        match = true;
                        temp = usedLet.substring(j,j+1);
                    }
                }
                if(match){
                    display = display + " "+temp;
                }else {
                    display = display + " " + "_";
                }
            }
            
        }
        label.setText(display);
        checkWin();
    }
    private void checkWin(){
        count=0;
        for(int i =0;i<word.length();i++){
            if(guess.compareTo(word.substring(i,i+1))==0){
                count++;
                corGuess++;
            }
        }
        if(count==0){
            if(incGuess==12){
                result.setText("YOU LOSE");
                endGame();
            }else{
                incGuess++;
            }
            setPics();
        }
        else{
            if(corGuess == word.length()){
                result.setText("YOU WIN");
                endGame();
            }
        }
        
        System.out.println("Cor: " +corGuess);
        System.out.println("Inc: " +incGuess);
    }
    private void setTopFive(){
        displayList.clear();
        topFive.setItems(displayList);
        int score = pointClass.getLetScore(usedLet);
        scores.add(score);
        int index = 0;
        for(int i = 0; i<scores.size();i++){
            int max = scores.get(i);
            index = i;
            for(int j = i+1; j< scores.size()-1;j++){
                if(scores.get(j)>max){
                    max = scores.get(j);
                    index = j;
                }
            }
            finList.add(i, names.get(index) + "        " + max);
        }
        if(finList.size()>5){
            for(int i = 0; i<6;i++){
                displayList.add(finList.get(i));
            }
        }else{
            for(int i = 0; i<finList.size();i++){
                displayList.add(finList.get(i));
            }
        }
        
        String outFile = "src/resources/file.txt";
        try {
                PrintWriter out = new PrintWriter(outFile);
                for(int i = 0; i < finList.size(); i++)
                {
                    out.println(finList.get(i));
                }
                out.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Something went wrong!");
        }
    }
    private void endGame(){
        setTopFive();
        System.out.println(names);
        System.out.println(scores);
        System.out.println(finList);
        topFive.setItems(displayList);
        String contin = JOptionPane.showInputDialog("Do you want to continue? Yes or No");
        if((contin.toUpperCase()).equals("YES")){
            word = "";
            displayBegin = "";
            display = "";
            usedLet = "";
            incGuess = 0;
            corGuess = 0;
            numPic = 0;
            chosen = false;
            name = JOptionPane.showInputDialog("What is your name?");
            names.add(name);
            guessedLet.setText(usedLet);
            length= (int) Math.floor(Math.random()*6)+4;
            possWords = dictClass.getArrayNoMatch(usedLet, length);
            display();
        }
        else if((contin.toUpperCase()).equals("NO")){
            result.setText("Game Over!");
        }
        else{
            result.setText("Game Over!");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
