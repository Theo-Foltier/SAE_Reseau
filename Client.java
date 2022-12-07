import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {

	public static void chatGlobal(){

		final Socket clientSocket;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc = new Scanner(System.in);

		System.out.println("Veuillez entrer votre pseudo, il sera affiché pour les autres utilisateurs:");
		Scanner nomSc = new Scanner(System.in);
		String nom = nomSc.nextLine();

		try {

			clientSocket = new Socket("127.0.0.1", 5000);

			out = new PrintWriter(clientSocket.getOutputStream());

			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			Thread envoyer = new Thread(new Runnable() {
				String msg;

				@Override
				public void run() {
					while (true) {
						msg = sc.nextLine();
						System.out.println(nom +": "+ msg); 
						out.println(nom + ": " + msg);
						out.flush();
					}
				}
			});
			envoyer.start();

			Thread recevoir = new Thread(new Runnable() {
				String msg;

				@Override
				public void run() {
					try {
						msg = in.readLine();
						while (msg != null) {
							System.out.println(msg);
							msg = in.readLine();
						}
						System.out.println("Serveur déconecté");
						out.close();
						clientSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			recevoir.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		System.out.println(" _________________________________");
		System.out.println("|            Bienvenue            |");
		System.out.println("|_________________________________|");
		System.out.println("");

		System.out.println("Veuillez entrer votre nom:");
		Scanner nomSc = new Scanner(System.in);
		String nom = nomSc.nextLine();

		System.out.println("Bonjour " + nom + " que voulez-vous faire ?");
		System.out.println("");
		System.out.println("1. Entrer dans un salon | 2. entrer dans le chat global | 3. Quitter l'application");
		Scanner numSc = new Scanner(System.in);
		int num = nomSc.nextInt();

		if(num == 2){chatGlobal();}
		else{System.exit(1);}
	}
}