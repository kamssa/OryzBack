package ci.gestion.metier.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.BeanIds;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	

	@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

@Override
	protected void configure(HttpSecurity http) throws Exception {
	        http
            .cors()
                .and()
            .csrf()
                .disable()
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                
            .authorizeRequests().antMatchers("/api/auth/**","/api/role/**","/api/siteEntreprise/**",
            		"/api/getPhoto/**","/api/travaux/**", "/api/rechemc/**",
            		"/api/getOperationByParam/**","/api/departement/**",
            		"/api/getDepartementByidEntreprise/**", "/api/autreAchat/**",
            		"/api/banque/**","/api/getStockGenralByidEntreprise/**",
            		"/api/achat/**","/api/achats/**","/api/autreAchatTravaux/**",
            		"/api/DeleteDetail/**","/api/listStockParEntreprise/**",
            		"/api/autres/**","/api/autre/**","/api/DeleteDetailAutre/**",
            		"/api/upload/**","/api/travauxByIdSite/**","/api/categorie/**",
            		"/api/transport/**","/api/transports/**","/api/listMatParEntreprise/**",
            		"/api/mainOeuvre/**","/api/mainOeuvres/**","/api/loyer/**","/api/loye/**","/api/detailSalaire/",
            		"/api/mainOeuvres/**","/api/DeleteDetailMain/**","/api/categorie/**",
            		"/api/getCategorieByidEntreprise/**","/api/stock/**",
            		"/api/getStockByidEntreprise/**","/api/detailStock/**",
            		"/api/materiel/**","/api/getMaterielByidCategorie/**",
            		"/api/montantStock/**","/api/detailStockHistory/**","/api/caisse/**",
            		"/api/getMontantStockByidEntreprise/**","/api/caisseDetail/**","/api/site/**",
            		"/api/detailAticleStockGeneral/**","/api/getCaisseDetailByIdEntreprise/**",
            		"/api/locations/**","/api/location/**","/api/evolution/**","/api/detailMainOeuvre/**",
            		"/api/detailAchatTravaux/**","/api/detailAutreAchatTravaux/**","/api/detailAutre/**",
            		"/api/detailLocation/**","/api/detailLoye/**","/api/detailTransport/**",
            		"/api/montantAutreAchat/**","/api/montantLoyer/**","/api/montantMainDoeuvre/**",
            		"/api/montantTransport/**","/api/montantAchatTravaux/**","/api/montantAutre/**",
            		"/api/montantLocation/**","/api/detailAutreAchatTravauxDate/**","/api/rechercheParDate/**",
            		"/api/detailAutreDate/**","/api/detailLocationDate/**","/api/detailLoyerDate/**",
            		"/api/detailMainDate/**","/api/detailTransportDate/**","/api/detailAchatTravauxDate/**"
            		).permitAll()
	       .anyRequest().authenticated();
	       
            // Add our custom JWT security filter
	        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

}

}
