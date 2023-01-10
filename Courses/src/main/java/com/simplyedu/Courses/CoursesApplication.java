package com.simplyedu.Courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CoursesApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoursesApplication.class, args);
	}

//	@Autowired
//	CoursePaginatedRepository courseRepository;
//	
//	@Autowired
//	CategoryRepository categoryRepository;
//	
//	@Bean
//	public ApplicationRunner testData(){
//		return (args) -> {
//			List<CategoryDbo> categoires = List.of(
//					new CategoryDbo(1L, "java"),
//					new CategoryDbo(2L,"german"),
//					new CategoryDbo(3L,"python"),
//					new CategoryDbo(4L,"programming"),
//					new CategoryDbo(5L,"expert"),
//					new CategoryDbo(6L,"bestseller"),
//					new CategoryDbo(7L,"matt"),
//					new CategoryDbo(8L,"non programming"),
//					new CategoryDbo(9L,"google"),
//					new CategoryDbo(10L,"javx"),
//					new CategoryDbo(11L,"rust")
//			);
//			
////			categoryRepository.saveAll(categoires);
//			
//			courseRepository.saveAll(List.of(
//					CourseDbo.builder()
//							.title("Java For Dummies")
//							.price(20)
//							.language("en")
//							.creationDate(LocalDate.of(2022, 5, 11))
//							.categories(List.of(new CategoryDbo("Java"), new CategoryDbo("programming")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("Java for Experts")
//							.price(25)
//							.language("en")
//							.creationDate(LocalDate.of(2021, 1, 6))
//							.categories(List.of(new CategoryDbo("Javax"), new CategoryDbo("hehe")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("Python 5")
//							.price(56)
//							.language("en")
//							.creationDate(LocalDate.of(2022, 12, 1))
//							.categories(List.of(new CategoryDbo("python"), new CategoryDbo("programming")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("Java und Python")
//							.price(200)
//							.language("de")
//							.creationDate(LocalDate.of(2012, 8, 11))
//							.categories(List.of(new CategoryDbo("c#"), new CategoryDbo("rust")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("How to program")
//							.price(15)
//							.language("en")
//							.creationDate(LocalDate.of(2020, 7, 1))
//							.categories(List.of(new CategoryDbo("Js"), new CategoryDbo("react")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("Das Matt")
//							.price(25)
//							.language("de")
//							.creationDate(LocalDate.of(2003, 12, 12))
//							.categories(List.of(new CategoryDbo("Js"), new CategoryDbo("angular")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("Rust Javx")
//							.price(560)
//							.language("en")
//							.creationDate(LocalDate.of(2021, 3, 3))
//							.categories(List.of(new CategoryDbo("Js"), new CategoryDbo("vue")))
//							.build(),
//
//					CourseDbo.builder()
//							.title("Googling like a master")
//							.price(200)
//							.language("en")
//							.creationDate(LocalDate.of(2019, 10, 30))
//							.categories(List.of(new CategoryDbo("Js"), new CategoryDbo("dino")))
//							.build()
//			));};
	//}
}
