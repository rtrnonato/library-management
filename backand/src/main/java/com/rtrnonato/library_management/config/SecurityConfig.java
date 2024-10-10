package com.rtrnonato.library_management.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * **Configuração de Segurança da Aplicação**
 * 
 * Esta classe configura os aspectos de segurança da aplicação, incluindo:
 * - Autenticação de usuários
 * - Autorização de acesso a recursos
 * - Criptografia de senhas
 * 
 * A classe utiliza o Spring Security para implementar essas funcionalidades.
 */
@Configuration
public class SecurityConfig {

	/**
     * **Configura a cadeia de filtros de segurança**
     * 
     * Este método configura a cadeia de filtros que interceptam as requisições HTTP e aplicam as regras de segurança.
     * 
     * @param http Objeto HttpSecurity utilizado para configurar a segurança.
     * @return A cadeia de filtros configurada.
     * @throws Exception Se ocorrer algum erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       /*
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/home", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/admin", true)
                .permitAll()
            )
            .logout(logout -> logout.permitAll());*/

        http
				.csrf(csrfCustomizer -> csrfCustomizer.disable()) // Desabilita CSRF
				.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll()); // Permite todas as requisições

    return http.build();
    }

    /**
     * **Serviço de detalhes do usuário**
     * 
     * Este método cria um serviço de detalhes do usuário em memória, 
     * configurando dois usuários: "user" e "admin" com suas respectivas senhas e papéis.
     * 
     * **Importante:** Em ambientes de produção, é altamente recomendado utilizar uma fonte de dados externa para armazenar as informações dos usuários.
     * 
     * @return O serviço de detalhes do usuário.
     */
   /* @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * **Codificador de senhas**
     * 
     * Este método cria um bean para o codificador de senhas BCryptPasswordEncoder.
     * O BCryptPasswordEncoder é um algoritmo forte e unidirecional, utilizado para proteger as senhas dos usuários.
     * 
     * @return O codificador de senhas.
     */
   /* @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}