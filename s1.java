import java.io.*;
import java.net.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class s1 {

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(5555);
      System.out.println("Listening for incoming connections...");

      Socket clientSocket = serverSocket.accept();
      System.out.println("Connection established :" + clientSocket.getPort());
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      );

      String g;
      do {
        String options =
          "ENTER WHICH TYPE OF ENCRYPTION DO YOU WANT! 1.Caesar cipher 2.XOR cipher ";
        out.println(options);

        // Receive and process client's choice
        String clientChoice = in.readLine();
        int choice = Integer.parseInt(clientChoice);

        switch (choice) {
          case 1:
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your data to send securely: ");
            String plainText = scanner.nextLine();
            plainText = plainText.toLowerCase();
            int mkey = findMostFrequentCharacter(plainText);
            String allLetters =
              "abcdefghijklmnopqrstuvwxyzABCDEFGHI" + "JKLMNOPQRSTUVWXYZ";

            // create a dictionary to store the substitution for the given alphabet in the plain text based on the key
            HashMap<Character, Character> dict1 = new HashMap<>();
            int key = mkey;
            System.out.println("key is:" + key);
            // Send the key to the client
            for (int i = 0; i < allLetters.length(); i++) {
              dict1.put(
                allLetters.charAt(i),
                allLetters.charAt((i + key) % allLetters.length())
              );
            }

            StringBuilder cipherText = new StringBuilder();

            // loop to generate ciphertext
            for (char c : plainText.toCharArray()) {
              if (allLetters.indexOf(c) != -1) {
                cipherText.append(dict1.get(c));
              } else {
                cipherText.append(c);
              }
            }

            System.out.println("your data is :" + cipherText);
            out.println(cipherText);
            out.println(key);
            break;
          case 2:
            Scanner scanner1 = new Scanner(System.in);
            System.out.print("Enter your data to send securely: ");
            String inputString = scanner1.nextLine();
            inputString = inputString.toLowerCase();
            char frequency = findMostFrequentCharacter1(inputString);
            char xorKey = (char) frequency;
            System.out.println("key is:" + xorKey);
            // Define String to store encrypted/decrypted String
            String outputString = "";

            // calculate length of input string
            int len = inputString.length();

            // perform XOR operation of key
            // with every character in string
            for (int i = 0; i < len; i++) {
              outputString =
                outputString +
                Character.toString((char) (inputString.charAt(i) ^ xorKey));
            }

            System.out.println("your data is: " + outputString);
            out.println(outputString);
            out.println(xorKey);
            break;
          default:
            out.println("Invalid choice");
        }

        out.println("do you want to continue [y/n]");

        g = in.readLine();
      } while ("y".equals(g));
      clientSocket.close();
      serverSocket.close();
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static int findMostFrequentCharacter(String str) {
    Map<Character, Integer> charFrequency = new HashMap<>();
    char mostFrequentChar = ' ';
    int maxFrequency = 0;

    for (char c : str.toCharArray()) {
      charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);

      for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
        if (entry.getValue() > maxFrequency) {
          mostFrequentChar = entry.getKey();
          maxFrequency = entry.getValue();
        }
      }
    }
    if (maxFrequency == 1) {
      int min = 1;
      int max = 25;
      Random random = new Random();
      int randomNumber = random.nextInt(max - min + 1) + min;
      maxFrequency = randomNumber;
      return maxFrequency;
    } else {
      return maxFrequency;
    }
    // Return the frequency as the key
  }

  private static char findMostFrequentCharacter1(String str) {
    Map<Character, Integer> charFrequency = new HashMap<>();
    char mostFrequentChar = ' ';
    int maxFrequency = 0;

    for (char c : str.toCharArray()) {
      charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);

      for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
        if (entry.getValue() > maxFrequency) {
          mostFrequentChar = entry.getKey();
          maxFrequency = entry.getValue();
        }
      }
    }
    if (maxFrequency == 1) {
      String characterSet = "abcdefghijklmnopqrstuvwxz";
      Random random = new Random();
      int randomIndex = random.nextInt(characterSet.length());
      mostFrequentChar = characterSet.charAt(randomIndex);
      return mostFrequentChar;
    } else {
      return mostFrequentChar;
    } // Return the frequency as the key
  }
}
