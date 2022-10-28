import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.sound.midi.Soundbank;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.pattern.FullLocationPatternConverter;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;
import org.bytedeco.javacpp.RealSense.intrinsics;

import com.omeraydogan.FollowerClass;
import com.omeraydogan.Following;

public class MainClass {

	public static void main(String[] args) {

		// 1- biografi bilgisini göster
		// 2- takipçi sayýný göster
		// 3- profil resminin linklini versin
		// 4- takipçi listesini getirsin
		// 5- takip ettiðim kiþileri getir
		// 6- uygulamyý sonlandýr çýkýþ

		Scanner veriScanner = new Scanner(System.in);

		String username = null;
		String password = null;
		String islemler = " 1- biografi bilgisini göster\r\n" + "		 2- takipçi sayýný göster\r\n"
				+ "		 3- profil resminin linklini versin\r\n" + "		 4- takipçi listesini getirsin\r\n"
				+ "		 5- takip ettiðim kiþileri getir\r\n" + "		 6- uygulamyý sonlandýr çýkýþ \r\n"
				+ "Ýþlem seçiniz: ";

		System.out.println("Instagram Projemize Hoþgelginiz...");

		System.out.print("Kullanýcý adýnýzý giriniz:");
		username = veriScanner.nextLine();

		System.out.print("þifreniniz giriniz:");
		password = veriScanner.nextLine();

		if (username.equals("omerjava") && password.equals("OmerJava+-*")) {
			// giriþ bilgileri doðru
			System.out.println("Kullanýcý adýnýz ve þifreniz doðrudur...");
			Instagram4j instagram = Instagram4j.builder().username(username).password(password).build();
			instagram.setup();

			try {

				// instagrama giriþ yaptýk ve kullanýcýya ail bilgileri eriþiyoruz...
				instagram.login();
				InstagramSearchUsernameResult userResult = instagram
						.sendRequest(new InstagramSearchUsernameRequest(username));

				System.out.println("Biografi : " + userResult.getUser().biography);
				// instagram üzerinden biografisine eriþtik

				System.out.print(islemler);
				String secim = veriScanner.nextLine();

				if (secim.equals("6")) {
					System.out.println("uygulama sonlandýrýlmýþtýr...");
				} else if (secim.equals("1")) {
					System.out.println("Biografi: " + userResult.getUser().biography);
				} else if (secim.equals("2")) {
					System.out.println("Takipçi Sayýsý: " + userResult.getUser().follower_count); //
					System.out.println("Takip ettiklerimin Sayýsý: " + userResult.getUser().following_count);
					// System.out.println("Takipçi Þehirleri: "+userResult.getUser().city_name);
					// System.out.println("Takipçi Asresleri:
					// "+userResult.getUser().address_street);
				} else if (secim.equals("3")) {
					// profil resminin linkini alma
					System.out.println("Profil resmimin linki: " + userResult.getUser().profile_pic_url);

				} else if (secim.equals("4")) {
					String takipciString = "1-mail gönder \n2-dosyaya yazdý \n3-console yazdýr \n4-hiçbirþey yapma";
					System.out.println(takipciString);
					String takipcisecim = veriScanner.nextLine();

					int sayac = 1;

					// takipçi listesini getirsin
					InstagramGetUserFollowersResult followerList = instagram
							.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));

					if (takipcisecim.equals("1")) {
						// mail gönder
						int i = 1;
						StringBuffer buffer = new StringBuffer();
						for (InstagramUserSummary follower : followerList.getUsers()) {

							buffer.append(i + ". " + follower.getPk() + " " + follower.getUsername() + " "
									+ follower.getFull_name() + "\n");
							i++;

						}
						MailGonder("omeraydoganjava@gmail.com", buffer.toString());

					}

					else if (takipcisecim.equals("2")) {
						// dosyaya yazdýr
						// List<InstagramUserSummary> users = followerList.getUsers();

						List<FollowerClass> fwArraylist = new ArrayList<>();

						for (InstagramUserSummary fw : followerList.getUsers()) {
							// ful name and username and pk degerini alacaðýz

							FollowerClass fwsinif = new FollowerClass();

							fwsinif.setPk(fw.getPk());// primery key dðeri

							fwsinif.setUsername(fw.getUsername());// username

							fwsinif.setFulname(fw.getFull_name());// full name

							fwArraylist.add(fwsinif);

						}

						File file = new File("C:\\Users\\MrCode\\Java Projelerim\\instagramFollewer\\follower.bin");

						if (!file.exists()) {

							file.createNewFile();

						}

						DosyayaYazdir(file, fwArraylist);

					} else if (takipcisecim.equals("3")) {
						// console yazdýr
						int i = 1;
						for (InstagramUserSummary follower : followerList.getUsers()) {
							System.out.println(i + ". " + follower.getPk() + " " + follower.getUsername() + " "
									+ follower.getFull_name());
							i++;
						}

					} else if (takipcisecim.equals("4")) {
						// hiçbirþey yapma
						System.out.println("yapýlacak iþleminiz bulunmamaktadýr. iþlem yapmak için tekrar deneyiniz.");
					} else {
						System.out.println("lütfen 1 ile 4 arsýnda bir seçim yapýnýz...");
					}
					for (InstagramUserSummary follower : followerList.getUsers()) {// takipcilerimin fulnameini dönüyor
						System.out.println(sayac + ". :" + follower.full_name);
						sayac++;

					}
				}

				else if (secim.equals("5")) {
					// takip ettiðim kiþileri getir

					InstagramGetUserFollowersRequest followingResult = Instagram
							.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));

					String takipciIslemler = "1-mail gönder " + "\n2-dosyaya yazdý " + "\n3-console yazdýr "
							+ "\n4-hiçbirþey yapma";
					System.out.println(takipciIslemler);
					String takipettiklerimsecim = veriScanner.nextLine();
					InstagramGetUserFollowersResult followerList;
					if (takipettiklerimsecim.equals("1")) {
						// mail gönder
						int i = 1;
						StringBuffer buffer = new StringBuffer();

						for (InstagramUserSummary following : followerList.getUsers()) {
							buffer.append(
									i + " ." + following.pk + " " + following.username + " " + following.full_name);
							i++;

						}
						MailGonder("omeraydoganjava@gmail.com", buffer.toString());// mail gönderdik
					} else if (takipettiklerimsecim.equals("2")) {

						// dosya yazdýrma

						List<Following> followings = new ArrayList<Following>();
						for (InstagramUserSummary fw : followingList.getUser()) {
							Following following = new Following(fw.getPk(), fw.getUsername(), fw.getFull_name());
							
							followings.add(following);
							
						}
						File file=new File("C:\\Users\\MrCode\\Java Projelerim\\instagramFollewer\bin");
						if (!file.exists()) {
							file.createNewFile();
							
						}
						else {
							WriteFollowingToFile(file, followings);	
						}
						

					} else if (takipettiklerimsecim.equals("3")) {

						int i=1;
						for (InstagramUserSummary following :followerList.getUsers()) {
							System.out.println(i + ". " + following.getPk() + " " + following.getUsername() + " "
									+ following.getFull_name());
							i++;
						}
						
						// console yazdýr
					} else if (takipettiklerimsecim.equals("4")) {
						// hiçbirþey yapma
						System.out.println("seçim yapýlmadý iþlem yapýlmaz");
					} else {
						System.out.println("Hatalý giriþ yaptýnýz lütfeen 1 ile 4 arasýnda deðer giriþi yapýnýz...");
					}
				}

			} catch (ClientProtocolException e) {
				System.out.println("Error : " + e.getMessage());

			} catch (IOException e) {
				System.out.println("Error : " + e.getMessage());
			}

		} else {
			System.out.println("kullanýcý adýnýz veya þifreniz yanlýþtýr...");
		}

	}

	public static void DosyayaYazdir(File file, List<FollowerClass> followers) // dosyayazdýr adýnda metot
																				// tanýmlýyoruz...
	{
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(followers);
			System.out.println("takipçi listesi baþarýlý bir þekilde dosyaya yazdýrýldý...");
		} catch (FileNotFoundException e) {
			System.out.println("hata : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("hata : " + e.getMessage());
		}

	}

	public static void MailGonder(String to, String icerik) {
		String fromEmail = "user maile";
		String fromPassword = "user password";

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.port", "587");

		Session sesesion = Session.getInstance(properties, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return null; // new PasswordAuthentication(fromEmail, fromPassword);
			}
		});

		try {
			Message message = new MimeMessage(sesesion);

			message.setFrom(new InternetAdress(fromEmail));

			message.setRecipient(RecipientType.TO, InternetAdress.parse(to));

			message.setSubject("Takipci listesi");

			message.setText(icerik);

			Transport.send(message);
			System.out.println("mail baþarýlý bir þekide gönderildi...");

		} catch (Exception e) {
			System.out.println("mail gönderirken hata oluþtu: " + e);
		}
	}

	public static void WriteFollowingToFile(File file, List<Following> following) // dosyayazdýr adýnda metot
	// tanýmlýyoruz...
	{
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(following);
			System.out.println("takipçi listesi baþarýlý bir þekilde dosyaya yazdýrýldý...");
		} catch (FileNotFoundException e) {
			System.out.println("hata : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("hata : " + e.getMessage());
		}

	}

}
