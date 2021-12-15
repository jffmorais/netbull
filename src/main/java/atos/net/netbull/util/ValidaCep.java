package atos.net.netbull.util;

public class ValidaCep {

	public Boolean cepValido(String cep) {
		if (cep.length() == 8)
			cep = cep.substring(0, 5) + "-" + cep.substring(5);

		return cep.matches("[0-9]{5}-[0-9]{3}");
	}
}
