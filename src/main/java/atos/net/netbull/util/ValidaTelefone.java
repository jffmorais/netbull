package atos.net.netbull.util;

public class ValidaTelefone {

	public boolean telefoneValido(String tel) {
		String pattern = "^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";  
		return tel.matches(pattern);
	}
}
