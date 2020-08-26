package ci.gestion;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import ci.gestion.dao.SiteRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailAchatTravauxRepository;


@SpringBootApplication
@EntityScan(basePackageClasses = { 
		GestionApplication.class,
		Jsr310JpaConverters.class 
})
public class GestionApplication implements CommandLineRunner{
	 private static final Logger logger = LoggerFactory.getLogger(GestionApplication.class);
	 @Autowired
	 private SiteRepository siteRepo;
	 @Autowired
	 private TravauxRepository travauxRepository;
	 @Autowired
	 private DetailAchatTravauxRepository detailAchatTravauxRepository;
	public static void main(String[] args) {
		SpringApplication.run(GestionApplication.class, args);
	}
	void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	}
	/*@Bean
	CommandLineRunner  start(SiteRepository siteRepo, TravauxRepository travauxRepository) {
		return args ->{
			siteRepo.deleteAll();  
			travauxRepository.deleteAll();
			Stream.of("construction").forEach(t->{
				siteRepo.save(new Site("Toumody", "Ville au centre"));
				Site site = siteRepo.findByLibelle("Toumody");
				travauxRepository.save(new Traveaux("construction"
						,5000000d,20000000d,site));
				
			});
			travauxRepository.findAll().forEach(System.out::println);
		};
		
	}*/
	@Override
	public void run(String... args) throws Exception {
		
	   // travauxRepository.deleteAll();
	  //  siteRepo.deleteAll();
	 
		//  siteRepo.save(new Site("Toumody", "Au centre"));
		 //  Site site = siteRepo.findByLibelle("Toumody");
		 /*  travauxRepository.save(new Travaux("Construction", 10000000d, 20000000d, new Site("Toumody", "Ville Au centre")));
		   travauxRepository.save(new Travaux("Destruction", 5000000d, 30000000d, new Site("Bocanda", "Ville Au centre est")));
           detailAchatTravauxRepository.save(new DetailAchatTravaux("Achat", new Materiaux("Beton"), 20d,2000d,4000d,new Fournisseur("IFAMCI")));
		    travauxRepository.findAll().forEach((travaux) -> {
		        logger.info("{}", travaux);
		    });
		    detailAchatTravauxRepository.findAll().forEach((detail) -> {
		        logger.info("{}", detail);
		    });*/
	}
}
