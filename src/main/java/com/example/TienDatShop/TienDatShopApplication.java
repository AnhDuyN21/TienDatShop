package com.example.TienDatShop;

import com.example.TienDatShop.entity.Account;
import com.example.TienDatShop.entity.Admin;
import com.example.TienDatShop.entity.enumeration.AccountStatus;
import com.example.TienDatShop.repository.AccountRepository;
import com.example.TienDatShop.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TienDatShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TienDatShopApplication.class, args);
	}
	@Bean
	CommandLineRunner initAdmin(AccountRepository accountRepo, AdminRepository adminRepo) {
		return args -> {
			if (accountRepo.findByEmail("admin@gmail.com") == null) {
				Account account = Account.builder()
						.name("Admin")
						.email("admin@gmail.com")
						.password(new BCryptPasswordEncoder().encode("123"))
						.status(AccountStatus.ACTIVE)
						.build();

				accountRepo.save(account);

				Admin admin = Admin.builder()
						.account(account)
						.build();

				adminRepo.save(admin);
			}
		};
	}
}
