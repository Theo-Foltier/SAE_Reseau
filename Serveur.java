import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Serveur {

	public static void main(String[] test) {

		final ServerSocket serveurSocket;
		final Socket clientSocket;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc = new Scanner(System.in);

		System.out.println(" _________________________________");
		System.out.println("|            Bienvenue            |");
		System.out.println("|_________________________________|");
		System.out.println("");



		System.out.println("Veuillez entrer votre pseudo:");
		Scanner nomSc = new Scanner(System.in);
		String nom = nomSc.nextLine();
		if(nom != null){
			try {
				serveurSocket = new ServerSocket(5000);
				clientSocket = serveurSocket.accept();
				out = new PrintWriter(clientSocket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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

							System.out.println("Client déconecté");
							
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
	}
}