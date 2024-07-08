package com.example.ex5springdavidzaydenbergronelian;

import com.example.ex5springdavidzaydenbergronelian.model.Product;
import com.example.ex5springdavidzaydenbergronelian.model.User;
import com.example.ex5springdavidzaydenbergronelian.repository.ProductRepository;
import com.example.ex5springdavidzaydenbergronelian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * This class initializes the database with sample data.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initializes the database with sample data.
     * @param args Command line arguments
     * @throws Exception If an error occurs
     */
    @Override
    public void run(String... args) throws Exception {
        initializeProducts();
        initializeDefaultUsers();
    }

    /**
     * Initializes the database with default users.
     */
    private void initializeProducts() {
        Product cigar1 = new Product();
        cigar1.setProductId("CIG001");
        cigar1.setName("Montecristo No. 2");
        cigar1.setCategory("Cigars");
        cigar1.setBrand("Montecristo");
        cigar1.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar1.setPrice(new BigDecimal("15.00"));
        cigar1.setStockQuantity(100);
        saveProductIfNotExists(cigar1);

        Product cigar2 = new Product();
        cigar2.setProductId("CIG002");
        cigar2.setName("Cohiba Robusto");
        cigar2.setCategory("Cigars");
        cigar2.setBrand("Cohiba");
        cigar2.setDescription("A classic cigar known for its quality and taste.");
        cigar2.setPrice(new BigDecimal("20.00"));
        cigar2.setStockQuantity(100);
        saveProductIfNotExists(cigar2);

        Product cigar3 = new Product();
        cigar3.setProductId("CIG003");
        cigar3.setName("Arturo Fuente Opus X");
        cigar3.setCategory("Cigars");
        cigar3.setBrand("Arturo Fuente");
        cigar3.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar3.setPrice(new BigDecimal("30.00"));
        cigar3.setStockQuantity(100);
        saveProductIfNotExists(cigar3);

        Product cigar4 = new Product();
        cigar4.setProductId("CIG004");
        cigar4.setName("Padron 1926 Serie");
        cigar4.setCategory("Cigars");
        cigar4.setBrand("Padron");
        cigar4.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar4.setPrice(new BigDecimal("25.00"));
        cigar4.setStockQuantity(100);
        saveProductIfNotExists(cigar4);

        Product cigar5 = new Product();
        cigar5.setProductId("CIG005");
        cigar5.setName("Ashton VSG");
        cigar5.setCategory("Cigars");
        cigar5.setBrand("Ashton");
        cigar5.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar5.setPrice(new BigDecimal("15.00"));
        cigar5.setStockQuantity(100);
        saveProductIfNotExists(cigar5);

        Product cigar6 = new Product();
        cigar6.setProductId("CIG006");
        cigar6.setName("Davidoff Winston Churchill");
        cigar6.setCategory("Cigars");
        cigar6.setBrand("Davidoff");
        cigar6.setDescription("A classic cigar known for its quality and taste.");
        cigar6.setPrice(new BigDecimal("20.00"));
        cigar6.setStockQuantity(100);
        saveProductIfNotExists(cigar6);

        Product cigar7 = new Product();
        cigar7.setProductId("CIG007");
        cigar7.setName("La Flor Dominicana Andalusian Bull");
        cigar7.setCategory("Cigars");
        cigar7.setBrand("La Flor Dominicana");
        cigar7.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar7.setPrice(new BigDecimal("30.00"));
        cigar7.setStockQuantity(100);
        saveProductIfNotExists(cigar7);

        Product cigar8 = new Product();
        cigar8.setProductId("CIG008");
        cigar8.setName("My Father Le Bijou 1922");
        cigar8.setCategory("Cigars");
        cigar8.setBrand("My Father");
        cigar8.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar8.setPrice(new BigDecimal("25.00"));
        cigar8.setStockQuantity(100);
        saveProductIfNotExists(cigar8);

        Product cigar9 = new Product();
        cigar9.setProductId("CIG009");
        cigar9.setName("Oliva Serie V");
        cigar9.setCategory("Cigars");
        cigar9.setBrand("Oliva");
        cigar9.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar9.setPrice(new BigDecimal("15.00"));
        cigar9.setStockQuantity(100);
        saveProductIfNotExists(cigar9);

        Product cigar10 = new Product();
        cigar10.setProductId("CIG010");
        cigar10.setName("Alec Bradley Prensado");
        cigar10.setCategory("Cigars");
        cigar10.setBrand("Alec Bradley");
        cigar10.setDescription("A classic cigar known for its quality and taste.");
        cigar10.setPrice(new BigDecimal("20.00"));
        cigar10.setStockQuantity(100);
        saveProductIfNotExists(cigar10);

        Product cigar11 = new Product();
        cigar11.setProductId("CIG011");
        cigar11.setName("Rocky Patel Decade");
        cigar11.setCategory("Cigars");
        cigar11.setBrand("Rocky Patel");
        cigar11.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar11.setPrice(new BigDecimal("30.00"));
        cigar11.setStockQuantity(100);
        saveProductIfNotExists(cigar11);

        Product cigar12 = new Product();
        cigar12.setProductId("CIG012");
        cigar12.setName("Tatuaje Fausto");
        cigar12.setCategory("Cigars");
        cigar12.setBrand("Tatuaje");
        cigar12.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar12.setPrice(new BigDecimal("25.00"));
        cigar12.setStockQuantity(100);
        saveProductIfNotExists(cigar12);

        Product cigar13 = new Product();
        cigar13.setProductId("CIG013");
        cigar13.setName("Liga Privada No. 9");
        cigar13.setCategory("Cigars");
        cigar13.setBrand("Drew Estate");
        cigar13.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar13.setPrice(new BigDecimal("15.00"));
        cigar13.setStockQuantity(100);
        saveProductIfNotExists(cigar13);

        Product cigar14 = new Product();
        cigar14.setProductId("CIG014");
        cigar14.setName("Crowned Heads Four Kicks");
        cigar14.setCategory("Cigars");
        cigar14.setBrand("Crowned Heads");
        cigar14.setDescription("A classic cigar known for its quality and taste.");
        cigar14.setPrice(new BigDecimal("20.00"));
        cigar14.setStockQuantity(100);
        saveProductIfNotExists(cigar14);

        Product cigar15 = new Product();
        cigar15.setProductId("CIG015");
        cigar15.setName("CAO Flathead V660 Carb");
        cigar15.setCategory("Cigars");
        cigar15.setBrand("CAO");
        cigar15.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar15.setPrice(new BigDecimal("30.00"));
        cigar15.setStockQuantity(100);
        saveProductIfNotExists(cigar15);

        Product cigar16 = new Product();
        cigar16.setProductId("CIG016");
        cigar16.setName("Avo XO");
        cigar16.setCategory("Cigars");
        cigar16.setBrand("Avo");
        cigar16.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar16.setPrice(new BigDecimal("25.00"));
        cigar16.setStockQuantity(100);
        saveProductIfNotExists(cigar16);

        Product cigar17 = new Product();
        cigar17.setProductId("CIG017");
        cigar17.setName("Macanudo Cafe");
        cigar17.setCategory("Cigars");
        cigar17.setBrand("Macanudo");
        cigar17.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar17.setPrice(new BigDecimal("15.00"));
        cigar17.setStockQuantity(100);
        saveProductIfNotExists(cigar17);

        Product cigar18 = new Product();
        cigar18.setProductId("CIG018");
        cigar18.setName("Punch Gran Puro");
        cigar18.setCategory("Cigars");
        cigar18.setBrand("Punch");
        cigar18.setDescription("A classic cigar known for its quality and taste.");
        cigar18.setPrice(new BigDecimal("20.00"));
        cigar18.setStockQuantity(100);
        saveProductIfNotExists(cigar18);

        Product cigar19 = new Product();
        cigar19.setProductId("CIG019");
        cigar19.setName("Hoyo de Monterrey Excalibur");
        cigar19.setCategory("Cigars");
        cigar19.setBrand("Hoyo de Monterrey");
        cigar19.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar19.setPrice(new BigDecimal("30.00"));
        cigar19.setStockQuantity(100);
        saveProductIfNotExists(cigar19);

        Product cigar20 = new Product();
        cigar20.setProductId("CIG020");
        cigar20.setName("Romeo y Julieta 1875");
        cigar20.setCategory("Cigars");
        cigar20.setBrand("Romeo y Julieta");
        cigar20.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar20.setPrice(new BigDecimal("25.00"));
        cigar20.setStockQuantity(100);
        saveProductIfNotExists(cigar20);

        Product cigar21 = new Product();
        cigar21.setProductId("CIG021");
        cigar21.setName("Camacho Corojo");
        cigar21.setCategory("Cigars");
        cigar21.setBrand("Camacho");
        cigar21.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar21.setPrice(new BigDecimal("15.00"));
        cigar21.setStockQuantity(100);
        saveProductIfNotExists(cigar21);

        Product cigar22 = new Product();
        cigar22.setProductId("CIG022");
        cigar22.setName("H. Upmann Vintage Cameroon");
        cigar22.setCategory("Cigars");
        cigar22.setBrand("H. Upmann");
        cigar22.setDescription("A classic cigar known for its quality and taste.");
        cigar22.setPrice(new BigDecimal("20.00"));
        cigar22.setStockQuantity(100);
        saveProductIfNotExists(cigar22);

        Product cigar23 = new Product();
        cigar23.setProductId("CIG023");
        cigar23.setName("La Aroma de Cuba Mi Amor");
        cigar23.setCategory("Cigars");
        cigar23.setBrand("La Aroma de Cuba");
        cigar23.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar23.setPrice(new BigDecimal("30.00"));
        cigar23.setStockQuantity(100);
        saveProductIfNotExists(cigar23);

        Product cigar24 = new Product();
        cigar24.setProductId("CIG024");
        cigar24.setName("Gurkha Ghost");
        cigar24.setCategory("Cigars");
        cigar24.setBrand("Gurkha");
        cigar24.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar24.setPrice(new BigDecimal("25.00"));
        cigar24.setStockQuantity(100);
        saveProductIfNotExists(cigar24);

        Product cigar25 = new Product();
        cigar25.setProductId("CIG025");
        cigar25.setName("Alec Bradley Black Market");
        cigar25.setCategory("Cigars");
        cigar25.setBrand("Alec Bradley");
        cigar25.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar25.setPrice(new BigDecimal("15.00"));
        cigar25.setStockQuantity(100);
        saveProductIfNotExists(cigar25);

        Product cigar26 = new Product();
        cigar26.setProductId("CIG026");
        cigar26.setName("Perdomo 10th Anniversary Champagne");
        cigar26.setCategory("Cigars");
        cigar26.setBrand("Perdomo");
        cigar26.setDescription("A classic cigar known for its quality and taste.");
        cigar26.setPrice(new BigDecimal("20.00"));
        cigar26.setStockQuantity(100);
        saveProductIfNotExists(cigar26);

        Product cigar27 = new Product();
        cigar27.setProductId("CIG027");
        cigar27.setName("San Cristobal Revelation");
        cigar27.setCategory("Cigars");
        cigar27.setBrand("San Cristobal");
        cigar27.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar27.setPrice(new BigDecimal("30.00"));
        cigar27.setStockQuantity(100);
        saveProductIfNotExists(cigar27);

        Product cigar28 = new Product();
        cigar28.setProductId("CIG028");
        cigar28.setName("EP Carrillo Encore");
        cigar28.setCategory("Cigars");
        cigar28.setBrand("EP Carrillo");
        cigar28.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar28.setPrice(new BigDecimal("25.00"));
        cigar28.setStockQuantity(100);
        saveProductIfNotExists(cigar28);

        Product cigar29 = new Product();
        cigar29.setProductId("CIG029");
        cigar29.setName("Joya de Nicaragua Antaño");
        cigar29.setCategory("Cigars");
        cigar29.setBrand("Joya de Nicaragua");
        cigar29.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar29.setPrice(new BigDecimal("15.00"));
        cigar29.setStockQuantity(100);
        saveProductIfNotExists(cigar29);

        Product cigar30 = new Product();
        cigar30.setProductId("CIG030");
        cigar30.setName("La Palina Goldie");
        cigar30.setCategory("Cigars");
        cigar30.setBrand("La Palina");
        cigar30.setDescription("A classic cigar known for its quality and taste.");
        cigar30.setPrice(new BigDecimal("20.00"));
        cigar30.setStockQuantity(100);
        saveProductIfNotExists(cigar30);

        Product cigar31 = new Product();
        cigar31.setProductId("CIG031");
        cigar31.setName("Nub Connecticut");
        cigar31.setCategory("Cigars");
        cigar31.setBrand("Nub");
        cigar31.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar31.setPrice(new BigDecimal("30.00"));
        cigar31.setStockQuantity(100);
        saveProductIfNotExists(cigar31);

        Product cigar32 = new Product();
        cigar32.setProductId("CIG032");
        cigar32.setName("CAO Brazilia");
        cigar32.setCategory("Cigars");
        cigar32.setBrand("CAO");
        cigar32.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar32.setPrice(new BigDecimal("25.00"));
        cigar32.setStockQuantity(100);
        saveProductIfNotExists(cigar32);

        Product cigar33 = new Product();
        cigar33.setProductId("CIG033");
        cigar33.setName("Partagas Black Label");
        cigar33.setCategory("Cigars");
        cigar33.setBrand("Partagas");
        cigar33.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar33.setPrice(new BigDecimal("15.00"));
        cigar33.setStockQuantity(100);
        saveProductIfNotExists(cigar33);

        Product cigar34 = new Product();
        cigar34.setProductId("CIG034");
        cigar34.setName("Aging Room Quattro Nicaragua");
        cigar34.setCategory("Cigars");
        cigar34.setBrand("Aging Room");
        cigar34.setDescription("A classic cigar known for its quality and taste.");
        cigar34.setPrice(new BigDecimal("20.00"));
        cigar34.setStockQuantity(100);
        saveProductIfNotExists(cigar34);

        Product cigar35 = new Product();
        cigar35.setProductId("CIG035");
        cigar35.setName("Padrón Family Reserve");
        cigar35.setCategory("Cigars");
        cigar35.setBrand("Padrón");
        cigar35.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar35.setPrice(new BigDecimal("30.00"));
        cigar35.setStockQuantity(100);
        saveProductIfNotExists(cigar35);

        Product cigar36 = new Product();
        cigar36.setProductId("CIG036");
        cigar36.setName("Arturo Fuente Hemingway");
        cigar36.setCategory("Cigars");
        cigar36.setBrand("Arturo Fuente");
        cigar36.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar36.setPrice(new BigDecimal("25.00"));
        cigar36.setStockQuantity(100);
        saveProductIfNotExists(cigar36);

        Product cigar37 = new Product();
        cigar37.setProductId("CIG037");
        cigar37.setName("Rocky Patel Sun Grown Maduro");
        cigar37.setCategory("Cigars");
        cigar37.setBrand("Rocky Patel");
        cigar37.setDescription("A premium cigar with a rich and smooth flavor.");
        cigar37.setPrice(new BigDecimal("15.00"));
        cigar37.setStockQuantity(100);
        saveProductIfNotExists(cigar37);

        Product cigar38 = new Product();
        cigar38.setProductId("CIG038");
        cigar38.setName("Oliva Serie O");
        cigar38.setCategory("Cigars");
        cigar38.setBrand("Oliva");
        cigar38.setDescription("A classic cigar known for its quality and taste.");
        cigar38.setPrice(new BigDecimal("20.00"));
        cigar38.setStockQuantity(100);
        saveProductIfNotExists(cigar38);

        Product cigar39 = new Product();
        cigar39.setProductId("CIG039");
        cigar39.setName("Alec Bradley Tempus");
        cigar39.setCategory("Cigars");
        cigar39.setBrand("Alec Bradley");
        cigar39.setDescription("A luxurious cigar with a bold and complex flavor profile.");
        cigar39.setPrice(new BigDecimal("30.00"));
        cigar39.setStockQuantity(100);
        saveProductIfNotExists(cigar39);

        Product cigar40 = new Product();
        cigar40.setProductId("CIG040");
        cigar40.setName("Tatuaje Miami");
        cigar40.setCategory("Cigars");
        cigar40.setBrand("Tatuaje");
        cigar40.setDescription("A highly-rated cigar with a rich and full-bodied flavor.");
        cigar40.setPrice(new BigDecimal("25.00"));
        cigar40.setStockQuantity(100);
        saveProductIfNotExists(cigar40);

        // Accessories
        Product accessory1 = new Product();
        accessory1.setProductId("ACC001");
        accessory1.setName("Cigar Cutter");
        accessory1.setCategory("Accessories");
        accessory1.setBrand("Xikar");
        accessory1.setDescription("A precision cigar cutter for a perfect cut every time.");
        accessory1.setPrice(new BigDecimal("10.00"));
        accessory1.setStockQuantity(100);
        saveProductIfNotExists(accessory1);

        Product accessory2 = new Product();
        accessory2.setProductId("ACC002");
        accessory2.setName("Cigar Case");
        accessory2.setCategory("Accessories");
        accessory2.setBrand("Cigar Caddy");
        accessory2.setDescription("A travel-friendly cigar case to protect your cigars.");
        accessory2.setPrice(new BigDecimal("20.00"));
        accessory2.setStockQuantity(100);
        saveProductIfNotExists(accessory2);

        Product accessory3 = new Product();
        accessory3.setProductId("ACC003");
        accessory3.setName("Cigar Ashtray");
        accessory3.setCategory("Accessories");
        accessory3.setBrand("Stinky");
        accessory3.setDescription("A durable cigar ashtray with ample space.");
        accessory3.setPrice(new BigDecimal("30.00"));
        accessory3.setStockQuantity(100);
        saveProductIfNotExists(accessory3);

        Product accessory4 = new Product();
        accessory4.setProductId("ACC004");
        accessory4.setName("Cigar Punch");
        accessory4.setCategory("Accessories");
        accessory4.setBrand("Colibri");
        accessory4.setDescription("A convenient cigar punch for a clean cut.");
        accessory4.setPrice(new BigDecimal("15.00"));
        accessory4.setStockQuantity(100);
        saveProductIfNotExists(accessory4);

        Product accessory5 = new Product();
        accessory5.setProductId("ACC005");
        accessory5.setName("Cigar Humidifier");
        accessory5.setCategory("Accessories");
        accessory5.setBrand("Boveda");
        accessory5.setDescription("A cigar humidifier to maintain ideal humidity.");
        accessory5.setPrice(new BigDecimal("10.00"));
        accessory5.setStockQuantity(100);
        saveProductIfNotExists(accessory5);

        Product accessory6 = new Product();
        accessory6.setProductId("ACC006");
        accessory6.setName("Cigar Lighter");
        accessory6.setCategory("Accessories");
        accessory6.setBrand("Vertigo");
        accessory6.setDescription("A reliable cigar lighter with a strong flame.");
        accessory6.setPrice(new BigDecimal("25.00"));
        accessory6.setStockQuantity(100);
        saveProductIfNotExists(accessory6);

        Product accessory7 = new Product();
        accessory7.setProductId("ACC007");
        accessory7.setName("Cigar Stand");
        accessory7.setCategory("Accessories");
        accessory7.setBrand("Havana Saver");
        accessory7.setDescription("A portable cigar stand for convenience.");
        accessory7.setPrice(new BigDecimal("20.00"));
        accessory7.setStockQuantity(100);
        saveProductIfNotExists(accessory7);

        Product accessory8 = new Product();
        accessory8.setProductId("ACC008");
        accessory8.setName("Cigar Hygrometer");
        accessory8.setCategory("Accessories");
        accessory8.setBrand("Xikar");
        accessory8.setDescription("A digital cigar hygrometer for accurate humidity reading.");
        accessory8.setPrice(new BigDecimal("25.00"));
        accessory8.setStockQuantity(100);
        saveProductIfNotExists(accessory8);

        Product accessory9 = new Product();
        accessory9.setProductId("ACC009");
        accessory9.setName("Cigar Scissors");
        accessory9.setCategory("Accessories");
        accessory9.setBrand("Credo");
        accessory9.setDescription("Sharp cigar scissors for a precise cut.");
        accessory9.setPrice(new BigDecimal("20.00"));
        accessory9.setStockQuantity(100);
        saveProductIfNotExists(accessory9);

        Product accessory10 = new Product();
        accessory10.setProductId("ACC010");
        accessory10.setName("Cigar Tube");
        accessory10.setCategory("Accessories");
        accessory10.setBrand("Visol");
        accessory10.setDescription("A stylish cigar tube for single cigar storage.");
        accessory10.setPrice(new BigDecimal("15.00"));
        accessory10.setStockQuantity(100);
        saveProductIfNotExists(accessory10);

        // Lighters
        Product lighter1 = new Product();
        lighter1.setProductId("LIG001");
        lighter1.setName("Jet Flame Lighter");
        lighter1.setCategory("Lighters");
        lighter1.setBrand("Xikar");
        lighter1.setDescription("A powerful jet flame lighter for cigars.");
        lighter1.setPrice(new BigDecimal("30.00"));
        lighter1.setStockQuantity(100);
        saveProductIfNotExists(lighter1);

        Product lighter2 = new Product();
        lighter2.setProductId("LIG002");
        lighter2.setName("Soft Flame Lighter");
        lighter2.setCategory("Lighters");
        lighter2.setBrand("Colibri");
        lighter2.setDescription("A classic soft flame lighter for cigars.");
        lighter2.setPrice(new BigDecimal("20.00"));
        lighter2.setStockQuantity(100);
        saveProductIfNotExists(lighter2);

        Product lighter3 = new Product();
        lighter3.setProductId("LIG003");
        lighter3.setName("Triple Torch Lighter");
        lighter3.setCategory("Lighters");
        lighter3.setBrand("Vertigo");
        lighter3.setDescription("A triple torch lighter with adjustable flames.");
        lighter3.setPrice(new BigDecimal("40.00"));
        lighter3.setStockQuantity(100);
        saveProductIfNotExists(lighter3);

        Product lighter4 = new Product();
        lighter4.setProductId("LIG004");
        lighter4.setName("Single Torch Lighter");
        lighter4.setCategory("Lighters");
        lighter4.setBrand("Zippo");
        lighter4.setDescription("A reliable single torch lighter for cigars.");
        lighter4.setPrice(new BigDecimal("15.00"));
        lighter4.setStockQuantity(100);
        saveProductIfNotExists(lighter4);

        Product lighter5 = new Product();
        lighter5.setProductId("LIG005");
        lighter5.setName("Quad Torch Lighter");
        lighter5.setCategory("Lighters");
        lighter5.setBrand("Lotus");
        lighter5.setDescription("A quad torch lighter for a perfect light.");
        lighter5.setPrice(new BigDecimal("50.00"));
        lighter5.setStockQuantity(100);
        saveProductIfNotExists(lighter5);

        Product lighter6 = new Product();
        lighter6.setProductId("LIG006");
        lighter6.setName("Electric Arc Lighter");
        lighter6.setCategory("Lighters");
        lighter6.setBrand("Tesla Coil");
        lighter6.setDescription("An innovative electric arc lighter for cigars.");
        lighter6.setPrice(new BigDecimal("35.00"));
        lighter6.setStockQuantity(100);
        saveProductIfNotExists(lighter6);

        Product lighter7 = new Product();
        lighter7.setProductId("LIG007");
        lighter7.setName("Windproof Lighter");
        lighter7.setCategory("Lighters");
        lighter7.setBrand("S.T. Dupont");
        lighter7.setDescription("A windproof lighter for outdoor use.");
        lighter7.setPrice(new BigDecimal("45.00"));
        lighter7.setStockQuantity(100);
        saveProductIfNotExists(lighter7);

        Product lighter8 = new Product();
        lighter8.setProductId("LIG008");
        lighter8.setName("Dual Flame Lighter");
        lighter8.setCategory("Lighters");
        lighter8.setBrand("Colibri");
        lighter8.setDescription("A dual flame lighter with precision control.");
        lighter8.setPrice(new BigDecimal("25.00"));
        lighter8.setStockQuantity(100);
        saveProductIfNotExists(lighter8);

        Product lighter9 = new Product();
        lighter9.setProductId("LIG009");
        lighter9.setName("Tabletop Lighter");
        lighter9.setCategory("Lighters");
        lighter9.setBrand("Xikar");
        lighter9.setDescription("A large tabletop lighter for convenience.");
        lighter9.setPrice(new BigDecimal("60.00"));
        lighter9.setStockQuantity(100);
        saveProductIfNotExists(lighter9);

        Product lighter10 = new Product();
        lighter10.setProductId("LIG010");
        lighter10.setName("Pocket Lighter");
        lighter10.setCategory("Lighters");
        lighter10.setBrand("Vertigo");
        lighter10.setDescription("A compact pocket lighter for everyday use.");
        lighter10.setPrice(new BigDecimal("10.00"));
        lighter10.setStockQuantity(100);
        saveProductIfNotExists(lighter10);

        // Humidors
        Product humidor1 = new Product();
        humidor1.setProductId("HUM001");
        humidor1.setName("Desktop Humidor");
        humidor1.setCategory("Humidors");
        humidor1.setBrand("Whytner");
        humidor1.setDescription("A stylish desktop humidor for cigar storage.");
        humidor1.setPrice(new BigDecimal("100.00"));
        humidor1.setStockQuantity(50);
        saveProductIfNotExists(humidor1);

        Product humidor2 = new Product();
        humidor2.setProductId("HUM002");
        humidor2.setName("Travel Humidor");
        humidor2.setCategory("Humidors");
        humidor2.setBrand("Cigar Caddy");
        humidor2.setDescription("A compact travel humidor for cigars on the go.");
        humidor2.setPrice(new BigDecimal("50.00"));
        humidor2.setStockQuantity(50);
        saveProductIfNotExists(humidor2);

        Product humidor3 = new Product();
        humidor3.setProductId("HUM003");
        humidor3.setName("Cabinet Humidor");
        humidor3.setCategory("Humidors");
        humidor3.setBrand("Baldwin");
        humidor3.setDescription("A large cabinet humidor for serious collectors.");
        humidor3.setPrice(new BigDecimal("500.00"));
        humidor3.setStockQuantity(50);
        saveProductIfNotExists(humidor3);

        Product humidor4 = new Product();
        humidor4.setProductId("HUM004");
        humidor4.setName("Glass Top Humidor");
        humidor4.setCategory("Humidors");
        humidor4.setBrand("Case Elegance");
        humidor4.setDescription("A glass top humidor to showcase your cigars.");
        humidor4.setPrice(new BigDecimal("120.00"));
        humidor4.setStockQuantity(50);
        saveProductIfNotExists(humidor4);

        Product humidor5 = new Product();
        humidor5.setProductId("HUM005");
        humidor5.setName("Electric Humidor");
        humidor5.setCategory("Humidors");
        humidor5.setBrand("NewAir");
        humidor5.setDescription("An electric humidor with precise humidity control.");
        humidor5.setPrice(new BigDecimal("200.00"));
        humidor5.setStockQuantity(50);
        saveProductIfNotExists(humidor5);

        Product humidor6 = new Product();
        humidor6.setProductId("HUM006");
        humidor6.setName("Acrylic Humidor");
        humidor6.setCategory("Humidors");
        humidor6.setBrand("Tightvac");
        humidor6.setDescription("A durable acrylic humidor for cigar storage.");
        humidor6.setPrice(new BigDecimal("80.00"));
        humidor6.setStockQuantity(50);
        saveProductIfNotExists(humidor6);

        Product humidor7 = new Product();
        humidor7.setProductId("HUM007");
        humidor7.setName("Vintage Humidor");
        humidor7.setCategory("Humidors");
        humidor7.setBrand("Prestige Import Group");
        humidor7.setDescription("A vintage-style humidor with classic appeal.");
        humidor7.setPrice(new BigDecimal("150.00"));
        humidor7.setStockQuantity(50);
        saveProductIfNotExists(humidor7);

        Product humidor8 = new Product();
        humidor8.setProductId("HUM008");
        humidor8.setName("Personal Humidor");
        humidor8.setCategory("Humidors");
        humidor8.setBrand("Boveda");
        humidor8.setDescription("A small personal humidor for everyday use.");
        humidor8.setPrice(new BigDecimal("40.00"));
        humidor8.setStockQuantity(50);
        saveProductIfNotExists(humidor8);

        Product humidor9 = new Product();
        humidor9.setProductId("HUM009");
        humidor9.setName("Premium Humidor");
        humidor9.setCategory("Humidors");
        humidor9.setBrand("Daniel Marshall");
        humidor9.setDescription("A premium humidor with luxurious features.");
        humidor9.setPrice(new BigDecimal("300.00"));
        humidor9.setStockQuantity(50);
        saveProductIfNotExists(humidor9);

        Product humidor10 = new Product();
        humidor10.setProductId("HUM010");
        humidor10.setName("Humidor Jar");
        humidor10.setCategory("Humidors");
        humidor10.setBrand("Le Veil");
        humidor10.setDescription("A humidor jar for small cigar collections.");
        humidor10.setPrice(new BigDecimal("60.00"));
        humidor10.setStockQuantity(50);
        saveProductIfNotExists(humidor10);

        // Cutters
        Product cutter1 = new Product();
        cutter1.setProductId("CUT001");
        cutter1.setName("V-Cut Cigar Cutter");
        cutter1.setCategory("Cutters");
        cutter1.setBrand("Colibri");
        cutter1.setDescription("A precision V-cut cigar cutter.");
        cutter1.setPrice(new BigDecimal("25.00"));
        cutter1.setStockQuantity(100);
        saveProductIfNotExists(cutter1);

        Product cutter2 = new Product();
        cutter2.setProductId("CUT002");
        cutter2.setName("Double Blade Cutter");
        cutter2.setCategory("Cutters");
        cutter2.setBrand("Xikar");
        cutter2.setDescription("A reliable double blade cutter for cigars.");
        cutter2.setPrice(new BigDecimal("30.00"));
        cutter2.setStockQuantity(100);
        saveProductIfNotExists(cutter2);

        Product cutter3 = new Product();
        cutter3.setProductId("CUT003");
        cutter3.setName("Punch Cutter");
        cutter3.setCategory("Cutters");
        cutter3.setBrand("Vertigo");
        cutter3.setDescription("A convenient punch cutter for a clean cut.");
        cutter3.setPrice(new BigDecimal("15.00"));
        cutter3.setStockQuantity(100);
        saveProductIfNotExists(cutter3);

        Product cutter4 = new Product();
        cutter4.setProductId("CUT004");
        cutter4.setName("Guillotine Cutter");
        cutter4.setCategory("Cutters");
        cutter4.setBrand("Credo");
        cutter4.setDescription("A sharp guillotine cutter for precision.");
        cutter4.setPrice(new BigDecimal("20.00"));
        cutter4.setStockQuantity(100);
        saveProductIfNotExists(cutter4);

        Product cutter5 = new Product();
        cutter5.setProductId("CUT005");
        cutter5.setName("Tabletop Cutter");
        cutter5.setCategory("Cutters");
        cutter5.setBrand("Lotus");
        cutter5.setDescription("A sturdy tabletop cutter for cigars.");
        cutter5.setPrice(new BigDecimal("50.00"));
        cutter5.setStockQuantity(100);
        saveProductIfNotExists(cutter5);

        Product cutter6 = new Product();
        cutter6.setProductId("CUT006");
        cutter6.setName("Scissor Cutter");
        cutter6.setCategory("Cutters");
        cutter6.setBrand("Xikar");
        cutter6.setDescription("A precision scissor cutter for cigars.");
        cutter6.setPrice(new BigDecimal("25.00"));
        cutter6.setStockQuantity(100);
        saveProductIfNotExists(cutter6);

        Product cutter7 = new Product();
        cutter7.setProductId("CUT007");
        cutter7.setName("Serrated Cutter");
        cutter7.setCategory("Cutters");
        cutter7.setBrand("Colibri");
        cutter7.setDescription("A serrated cutter for an easy cut.");
        cutter7.setPrice(new BigDecimal("30.00"));
        cutter7.setStockQuantity(100);
        saveProductIfNotExists(cutter7);

        Product cutter8 = new Product();
        cutter8.setProductId("CUT008");
        cutter8.setName("Keychain Cutter");
        cutter8.setCategory("Cutters");
        cutter8.setBrand("Vertigo");
        cutter8.setDescription("A keychain cutter for portability.");
        cutter8.setPrice(new BigDecimal("10.00"));
        cutter8.setStockQuantity(100);
        saveProductIfNotExists(cutter8);

        Product cutter9 = new Product();
        cutter9.setProductId("CUT009");
        cutter9.setName("Spring-Loaded Cutter");
        cutter9.setCategory("Cutters");
        cutter9.setBrand("S.T. Dupont");
        cutter9.setDescription("A spring-loaded cutter for ease of use.");
        cutter9.setPrice(new BigDecimal("35.00"));
        cutter9.setStockQuantity(100);
        saveProductIfNotExists(cutter9);

        Product cutter10 = new Product();
        cutter10.setProductId("CUT010");
        cutter10.setName("Stainless Steel Cutter");
        cutter10.setCategory("Cutters");
        cutter10.setBrand("Lotus");
        cutter10.setDescription("A durable stainless steel cutter.");
        cutter10.setPrice(new BigDecimal("40.00"));
        cutter10.setStockQuantity(100);
        saveProductIfNotExists(cutter10);

    }

    /**
     * Saves a product if it does not already exist in the database.
     *
     * @param product the product to save
     */
    private void saveProductIfNotExists(Product product) {
        Optional<Product> existingProduct = productRepository.findByProductId(product.getProductId());
        if (existingProduct.isEmpty())
            productRepository.save(product);
    }

    /**
     * Initializes the default users in the database.
     */
    private void initializeDefaultUsers() {
        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("@dm1nP@ssw0rd!");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@example.com");
            admin.setDateOfBirth(LocalDate.of(1990, 1, 1));
            admin.setPhoneNumber("1234567890");
            admin.setRole("ROLE_ADMIN");
            userService.save(admin);
        }

        if (userService.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("P@ssW0rd123!");
            user.setFirstName("Default");
            user.setLastName("User");
            user.setEmail("user@example.com");
            user.setDateOfBirth(LocalDate.of(1995, 1, 1));
            user.setPhoneNumber("1234567890");
            user.setRole("ROLE_USER");
            userService.save(user);
        }
    }
}
