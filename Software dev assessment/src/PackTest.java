import org.junit.Test;

import java.io.*;
import java.util.Random;

import static org.junit.Assert.*;

public class PackTest {
        @Test
        public void TestWritePlayers() throws IOException {
                String testString =  "Player " + 1 + " picked up " + 3
                        + " from player " + 3 + " and discarded " + 4
                        + " to player " + 2;
                String line = null;
                String filename = "player" + 1 + "output.txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        PrintWriter writer = new PrintWriter(myObj);
                        writer.print("");
                        writer.close();
                        FileWriter fr = new FileWriter(myObj, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(testString);
                        br.newLine();
                        br.close();
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String st;
                        while ((st = bufferedReader.readLine()) != null) {
                                 line = st;
                        }
                        fr.close();
                }
                assertEquals(line, testString);
        }
        @Test
        public void TestPlayerFileCheckExists(){
                boolean testExists = false;
                String filename = "player" + 1 + "output.txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        testExists = true;
                }
                assertTrue(testExists);
        }

        @Test
        public void TestPlayerFileCheckNotExists(){
                boolean testExists = false;
                String filename = "playerNullOutput.txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        testExists = true;
                }
                assertFalse(testExists);
        }
        @Test
        public void TestWinnerWrite() throws IOException {
                String testString =  "player " + 1 + " final hand: 1 1 1 1";
                String Winline = null;
                String filename = "player" + 1 + "output.txt";
                File myObj = new File(filename);
                if (myObj.exists()){
                        PrintWriter writer = new PrintWriter(myObj);
                        writer.print("");
                        writer.close();
                        FileWriter fr = new FileWriter(myObj, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(testString);
                        br.newLine();
                        br.close();
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String st;
                        while ((st = bufferedReader.readLine()) != null) {
                                Winline = st;
                        }
                        fr.close();
                }
                assertEquals(Winline, testString);
        }
        @Test
        public void TestLoserWrite() throws IOException {
                String testString =  "player " + 1 + " has informed player " + 2 + " they have won."+ " Players final hand: 2 2 3 5";
                String looseline = null;
                String filename = "player" + 1 + "output.txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        PrintWriter writer = new PrintWriter(myObj);
                        writer.print("");
                        writer.close();
                        FileWriter fr = new FileWriter(myObj, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(testString);
                        br.newLine();
                        br.close();
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String st;
                        while ((st = bufferedReader.readLine()) != null) {
                                looseline = st;
                        }
                        fr.close();
                }
                assertEquals(looseline, testString);
        }
        @Test
        public void TestDeckWrite() throws IOException {
                String testString =  "Player  " + 1 + " final Deck: 2 6 3 5";
                String deckLine = null;
                String filename = "Deck_" + 1 + ".txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        PrintWriter writer = new PrintWriter(myObj);
                        writer.print("");
                        writer.close();
                        FileWriter fr = new FileWriter(myObj, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(testString);
                        br.newLine();
                        br.close();
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String st;
                        while ((st = bufferedReader.readLine()) != null) {
                                deckLine = st;
                        }
                        fr.close();
                }
                assertEquals(deckLine, testString);
        }

        @Test
        public void TestFileCheck(){
                boolean testExists = false;
                String filename = "test1.txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        testExists = true;
                }
                assertTrue(testExists);
        }
        @Test
        public void TestShuffleArray(){
                boolean testShuffle = false;
                Card[] packArray = new Card[4*8];
                Card[] staticArray = new Card[4*8];
                int arraySection = 0;
                for (int i = 0; i < 4; i++) {
                        for (int j = 1; j < 4; j++) {
                                packArray[arraySection] = new Card(j);
                                staticArray[arraySection] = new Card(j);
                                arraySection++;
                        }
                }
                Random rand = new Random();
                for (int i = 0; i < packArray.length; i++) {
                        int randomIndexToSwap = rand.nextInt(packArray.length);
                        Card temp = packArray[randomIndexToSwap];
                        packArray[randomIndexToSwap] = packArray[i];
                        packArray[i] = temp;
                }
                if(staticArray != packArray){
                        testShuffle = true;
                }
                assertTrue(testShuffle);
        }
        @Test
        public void TestWritePack() throws IOException {
                boolean test = true;
                Card [] testArray = new Card[4];
                Card[] deckLine = new Card[4];
                for (int i = 0; i < testArray.length; i++) {
                        testArray[i] = new Card(i);
                }
                String filename = "testWrite.txt";
                File myObj = new File(filename);
                if (myObj.exists()) {
                        PrintWriter writer = new PrintWriter(myObj);
                        writer.print("");
                        writer.close();
                        FileWriter fr = new FileWriter(myObj, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        for (Card card : deckLine) {
                                int a = card.getIndexValue();
                                br.write(String.valueOf(a));
                                br.newLine();
                        }
                        br.close();
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
                        String st;
                        int count = 0;
                        while ((st = bufferedReader.readLine()) != null) {
                                deckLine[count] = new Card(Integer.parseInt(st));
                                count++;
                        }
                        fr.close();
                        for (int i = 0; i < 4; i++) {
                                if(testArray[i].getIndexValue() != deckLine[i].getIndexValue()){
                                        test = false;
                                }
                        }
                }
                assertTrue(test);
        }
        @Test
        public void TestReadPack() throws IOException {
                boolean test = true;
                Card[] pack = new Card[32];
                Card[] testPack = new Card[32];
                File packFile = new File("test1.txt");
                BufferedReader br = new BufferedReader(new FileReader(packFile));
                String st;
                int count = 0;
                while ((st = br.readLine()) != null) {
                        pack[count] = new Card(Integer.parseInt(st));
                        testPack[count] = new Card(Integer.parseInt(st));
                        count++;
                }
                br.close();
                for (int i = 0; i < 4; i++) {
                        if(pack[i].getIndexValue() != pack[i].getIndexValue()){
                                test = false;
                        }
                }
                assertTrue(test);
        }
}