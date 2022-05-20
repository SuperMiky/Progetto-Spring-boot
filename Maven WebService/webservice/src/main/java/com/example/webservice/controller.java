package com.example.webservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

@CrossOrigin(origins = "*")
@RestController
public class controller {
	@GetMapping(value = "/register.php", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String Register(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		String risultato = "";

		Response response = new Response();

		Connection conn = DBconnection.createNewDBconnection();

		try {
			String query1 = "SELECT Username, Password FROM utenti where Username='" + username + "' AND Password='"
					+ HashPwd.getMd5(password) + "'";
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ResultSet ris1 = ps1.executeQuery();
			if (ris1.next()) {
				risultato = response.error("utente gia registrato");
			} else {
				String query2 = "INSERT INTO utenti(Username, Password) VALUES(?, ?)";
				PreparedStatement ps2 = conn.prepareStatement(query2);

				ps2.setString(1, username);
				ps2.setString(2, HashPwd.getMd5(password));
				ps2.executeUpdate();
				risultato = response.ok("utente registrato");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@GetMapping(value = "/getToken.php", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String GetToken(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		String risultato = "";

		Connection conn = DBconnection.createNewDBconnection();
		Response response = new Response();
		try {

			String query1 = "SELECT Id FROM utenti where Username='" + username + "' AND Password='"
					+ HashPwd.getMd5(password) + "'";
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ResultSet ris1 = ps1.executeQuery();
			if (ris1.next()) { // l'utente è presente
				int id = ris1.getInt("Id");
				int leftLimit = 48; // numeral '0'
				int rightLimit = 122; // letter 'z'
				int targetStringLength = 32;
				Random random = new Random();

				String token = random.ints(leftLimit, rightLimit + 1)
						.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
						.limit(targetStringLength)
						.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
						.toString();

				String query2 = "UPDATE utenti SET Token='" + token + "' Where Id = '" + id + "'";
				PreparedStatement ps2 = conn.prepareStatement(query2);
				ps2.executeUpdate();
				risultato = response.ok("{token:" + token + "}");
			} else {
				risultato = response.error("username o password errati");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@GetMapping(value = "/setString.php", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String SetString(@RequestParam("token") String token,
			@RequestParam("key") String key, @RequestParam("string") String string) {

		String risultato = "";

		Connection conn = DBconnection.createNewDBconnection();
		Response response = new Response();
		try {

			String query1 = "SELECT Id FROM utenti where Token ='" + token + "'";
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ResultSet ris1 = ps1.executeQuery();
			if (ris1.next()) {
				String query2 = "INSERT INTO stringhe(Token, Identificativo, Contenuto) VALUES(?, ?, ?)";
				PreparedStatement ps2 = conn.prepareStatement(query2);
				ps2.setString(1, token);
				ps2.setString(2, key);
				ps2.setString(3, string);
				ps2.executeUpdate();

				risultato = response.ok("stringa inserita con successo");
			} else {
				risultato = response.error("Token non esistente");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@GetMapping(value = "/getString.php", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String SetString(@RequestParam("token") String token,
			@RequestParam("key") String key) {

		String risultato = "";

		Connection conn = DBconnection.createNewDBconnection();
		Response response = new Response();
		try {

			String query1 = "SELECT Id FROM utenti where Token ='" + token + "'";
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ResultSet ris1 = ps1.executeQuery();
			if (ris1.next()) { // il token esiste
				String query2 = "SELECT Contenuto FROM stringhe where Token ='" + token + "' AND Identificativo = '"
						+ key + "'";

				PreparedStatement ps2 = conn.prepareStatement(query2);
				ResultSet ris2 = ps2.executeQuery();

				if (ris2.next()) // esiste un contenuto con quella key
				{
					String contenuto = ris2.getString("Contenuto");
					risultato = response.ok(contenuto);

				} else {
					risultato = response.error("identificativo non esistente");
				}

			} else {
				risultato = response.error("Token non esistente");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@GetMapping(value = "/deleteString.php", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteString(@RequestParam("token") String token,
			@RequestParam("key") String key) {

		String risultato = "";

		Connection conn = DBconnection.createNewDBconnection();
		Response response = new Response();
		try {

			String query1 = "SELECT Id FROM utenti where Token ='" + token + "'";
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ResultSet ris1 = ps1.executeQuery();
			if (ris1.next()) { // il token esiste
				String query2 = "DELETE FROM stringhe where Token = '" + token + "' AND Identificativo = '"
						+ key + "'";
				PreparedStatement ps2 = conn.prepareStatement(query2);
				ps2.executeUpdate();
				risultato = response.ok("contenuto eliminato");

			} else {
				risultato = response.error("Token non esistente");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultato;
	}

	@GetMapping(value = "/getKeys.php", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getKeys(@RequestParam("token") String token) {

		String risultato = "";

		Connection conn = DBconnection.createNewDBconnection();
		Response response = new Response();
		try {

			String query1 = "SELECT Id FROM utenti where Token ='" + token + "'";
			PreparedStatement ps1 = conn.prepareStatement(query1);
			ResultSet ris1 = ps1.executeQuery();
			if (ris1.next()) { // il token esiste

				String query2 = "SELECT Identificativo FROM stringhe where Token = " + token + "";
				PreparedStatement ps2 = conn.prepareStatement(query2);
				ResultSet ris2 = ps2.getResultSet();
				if (ris2.next()) {
					String keyS = ris2.getString("Identificativo");
					risultato += response.ok(keyS);
				}
			} else {
				risultato = response.error("Token non esistente");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return risultato;
	}
}