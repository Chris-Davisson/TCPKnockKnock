//Christopher Davisson
//10/22/2020
//330 Networking
//Assingment 2

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.regex.*;

public class TCPServer {
    public static void main(String[] argv) throws Exception{
        String clientSentence;

        //2D Jokes array, 0 = setup , 1 = joke
        String[][] jokes = {{"hawaii" , "I\'m fine, Hawaii you?"} , {"Voodoo" , "Voodoo you think you are, asking me so many questions?"} , {"Nana" , "Nana your business."} ,
                {"Hatch" , "God bless you."} , {"Mustache" , "Mustache you a question, but I'll shave it for later"} , {"Tank" , "You're welcome."}};

        //Provided code from lecture
        //Handles the Networking
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFomClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());


        //Initial contact from client
        clientSentence = inFomClient.readLine().toLowerCase();

        //Insuring input is correct
        while(!(Pattern.matches("tell me a joke[.]?" , clientSentence))){
            outToClient.writeBytes("Invalid start."+ "\n");
            System.out.println(clientSentence);
            clientSentence = inFomClient.readLine().toLowerCase();
        }

        boolean repeat = false;
        do{

            outToClient.writeBytes("Knock Knock" + "\n");
            clientSentence = inFomClient.readLine().toLowerCase();

            while(!(Pattern.matches("who[']?s there[?]?" , clientSentence))){
                outToClient.writeBytes("Invalid. response ask \"Who's there?\"." + "\n");
                clientSentence = inFomClient.readLine();
            }

            //Random Joke is chosen
            Random rand = new Random();
            int jokeNum = rand.nextInt(jokes.length);
            outToClient.writeBytes(jokes[jokeNum][0] + "\n");
            clientSentence = inFomClient.readLine().toLowerCase();

            while(!(Pattern.matches(jokes[jokeNum][0].toLowerCase() + " who[?]?",clientSentence))){
                outToClient.writeBytes("Invalid response to joke" + "\n");
                clientSentence = inFomClient.readLine();
            }

            //Joke is finished
            outToClient.writeBytes(jokes[jokeNum][1] + "\n");
            clientSentence = inFomClient.readLine();
            outToClient.writeBytes("Do you want to hear another one?" + "\n");
            clientSentence = inFomClient.readLine().toLowerCase();

            while(!(clientSentence.equals("yes") || clientSentence.equals("no"))){
                outToClient.writeBytes("Yes or No?" + "\n");
                clientSentence = inFomClient.readLine().toLowerCase();
            }

            if(clientSentence.equals("yes"))
                repeat = true;
            else
                repeat = false;

        }while (repeat);

        //Trying to break connection
        outToClient.writeBytes("done" + "\n");

        connectionSocket.close();
    }
}
