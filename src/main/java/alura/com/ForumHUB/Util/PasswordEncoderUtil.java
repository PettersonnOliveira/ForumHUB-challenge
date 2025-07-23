package alura.com.ForumHUB.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        String senhaOriginal = "123456"; // A senha que você quer criptografar
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = passwordEncoder.encode(senhaOriginal);

        System.out.println("Senha Original: " + senhaOriginal);
        System.out.println("Senha Criptografada (BCrypt): " + senhaCriptografada);
    }
}
