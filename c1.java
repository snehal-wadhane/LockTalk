import java.io.*;
import java.net.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class c1 {

  public static void main(String[] args) {
    try {
      Socket clientSocket = new Socket("192.168.154.58", 5555);

      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      );
      BufferedReader userInput = new BufferedReader(
        new InputStreamReader(System.in)
      );

      System.out.println("Connected to the server successfully");
      String u;
      do { // Receive and display options from the server
        String option;
        while ((option = in.readLine()) != null) {
          System.out.println(option);
          break; // Break after displaying the options
        }

        // Prompt the user for input and send it to the server
        System.out.print("Enter your choice (1 or 2): ");
        String userChoice = userInput.readLine();
        out.println(userChoice);
        int choice1 = Integer.parseInt(userChoice);
        String response = in.readLine();
        System.out.println("Server response: " + response);
        switch (choice1) {
          case 1:
            {
              String allLetters =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHI" + "JKLMNOPQRSTUVWXYZ";
              String r = in.readLine();
              int s = Integer.parseInt(r);
              int key = s;
              Map<Character, Character> dict2 = new HashMap<>();
              for (int i = 0; i < allLetters.length(); i++) {
                dict2.put(
                  allLetters.charAt(i),
                  allLetters.charAt(
                    (i - key + allLetters.length()) % allLetters.length()
                  )
                );
              }

              StringBuilder decryptedText = new StringBuilder();

              // loop to recover plain text
              for (char c : response.toString().toCharArray()) {
                if (allLetters.indexOf(c) != -1) {
                  decryptedText.append(dict2.get(c));
                } else {
                  decryptedText.append(c);
                }
              }

              System.out.println("Recovered plain text: " + decryptedText);
              break;
            }
          case 2:
            {
              char xorKey = in.readLine().charAt(0);

              // Define String to store encrypted/decrypted String
              String outputString = "";

              // calculate length of input string
              int len = response.length();

              // perform XOR operation of key
              // with every character in string
              for (int i = 0; i < len; i++) {
                outputString =
                  outputString +
                  Character.toString((char) (response.charAt(i) ^ xorKey));
              }

              System.out.println("Recovered plain text: " + outputString);

              break;
            }
          default:
            System.out.println("invalid input");
        }
        // Receive and display the server's response
        String r = in.readLine();
        System.out.println(r);
        u = userInput.readLine();
        out.println(u);
      } while ("y".equals(u));
      clientSocket.close();
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
