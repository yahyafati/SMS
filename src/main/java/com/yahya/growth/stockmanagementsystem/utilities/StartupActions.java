package com.yahya.growth.stockmanagementsystem.utilities;

import com.yahya.growth.stockmanagementsystem.model.*;
import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.security.Permission;
import com.yahya.growth.stockmanagementsystem.security.UserRole;
import com.yahya.growth.stockmanagementsystem.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class StartupActions {

        private final Environment environment;

        @Autowired
        public StartupActions(Environment environment) {
                this.environment = environment;
        }

        @EventListener({ ApplicationReadyEvent.class })
        public void applicationReadyEvent() {
                System.out.println("\n\n\nSPRING BOOT HAS STARTED\n\n\n");
                // System.out.println("Application started ... launching browser now");
                System.out.println("Spring boot is now running on the following address:\n" +
                                "http://localhost:" + environment.getProperty("server.port"));
                // browse("http://localhost:" + environment.getProperty("server.port"));
        }

        public static void browse(String url) {
                if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                                desktop.browse(new URI(url));
                        } catch (IOException | URISyntaxException e) {
                                e.printStackTrace();
                        }
                } else {
                        Runtime runtime = Runtime.getRuntime();
                        try {
                                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        @Bean
        public CommandLineRunner categoryData(
                        CategoryService categoryService, SubcategoryService subcategoryService,
                        BrandService brandService,
                        ItemService itemService, CustomerService customerService) {
                return args -> {
                        if (!categoryService.findAll().isEmpty() ||
                                        !subcategoryService.findAll().isEmpty() ||
                                        !brandService.findAll().isEmpty() ||
                                        !itemService.findAll().isEmpty() ||
                                        !customerService.findAll().isEmpty()) {
                                return;
                        }
                        Category electronicsCategory = Category.builder()
                                        .name("Electronics")
                                        .description("Electronics Stuff")
                                        .build();
                        Category computerCategory = Category.builder()
                                        .name("Computer")
                                        .description("Computer & Accessories")
                                        .build();
                        Category smartHomeCategory = Category.builder()
                                        .name("Smart Home")
                                        .description("Smarter House")
                                        .build();
                        Category softwareCategory = Category.builder()
                                        .name("Software")
                                        .description("Digital Items")
                                        .build();
                        electronicsCategory = categoryService.save(electronicsCategory);
                        computerCategory = categoryService.save(computerCategory);
                        smartHomeCategory = categoryService.save(smartHomeCategory);
                        softwareCategory = categoryService.save(softwareCategory);

                        Subcategory mobileSubcategory = Subcategory.builder()
                                        .name("Mobiles & Accessories")
                                        .description("Mobiles & Accessories")
                                        .category(electronicsCategory)
                                        .build();
                        Subcategory tvSubcategory = Subcategory.builder()
                                        .name("TV")
                                        .description("Televisions")
                                        .category(electronicsCategory)
                                        .build();
                        Subcategory laptopSubcategory = Subcategory.builder()
                                        .name("Laptop")
                                        .description("Laptops")
                                        .category(computerCategory)
                                        .build();
                        Subcategory alexaSubcategory = Subcategory.builder()
                                        .name("Alexa")
                                        .description("Alexa the pigeon")
                                        .category(smartHomeCategory)
                                        .build();
                        subcategoryService.save(mobileSubcategory);
                        subcategoryService.save(alexaSubcategory);
                        subcategoryService.save(laptopSubcategory);
                        subcategoryService.save(tvSubcategory);

                        Brand appleBrand = Brand.builder()
                                        .name("Apple")
                                        .description("Expensive Shit")
                                        .build();
                        Brand samsungBrand = Brand.builder()
                                        .name("Samsung Inc.")
                                        .description("Slightly Cheaper than Apple")
                                        .build();
                        Brand lgBrand = Brand.builder()
                                        .name("LG")
                                        .description("Life is Good")
                                        .build();
                        Brand amazonBrand = Brand.builder()
                                        .name("Amazon Inc.")
                                        .description("The biggest")
                                        .build();
                        Brand microsoftBrand = Brand.builder()
                                        .name("Microsoft Inc.")
                                        .description("One of tht biggest ones out there")
                                        .build();
                        brandService.save(lgBrand);
                        brandService.save(appleBrand);
                        brandService.save(samsungBrand);
                        brandService.save(amazonBrand);
                        brandService.save(microsoftBrand);

                        java.util.List<String> itemNames = Arrays.asList(
                                        "boom box", "spring", "hair tie", "floor", "newspaper", "tissue box",
                                        "headphones", "picture frame", "pillow", "shirt");
                        java.util.List<String> itemDescriptionStrings = Arrays.asList(
                                        "Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.",
                                        "Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.",
                                        "A small river named Duden flows by their place and supplies it with the necessary regelialia.",
                                        "It is a paradisematic country, in which roasted parts of sentences fly into your mouth.",
                                        "Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.",
                                        "The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen.",
                                        "She packed her seven versalia, put her initial into the belt and made herself on the way.",
                                        "When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane.",
                                        "Pityful a rethoric question ran over her cheek, then",
                                        "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin.",
                                        "He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.",
                                        "The bedding was hardly able to cover it and seemed ready to slide off any moment.",
                                        "His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked.",
                                        "\"What's happened to me? \" he thought. It wasn't a dream.",
                                        "His room, a proper human room although a little too small, lay peacefully between its four familiar walls.",
                                        "A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame.",
                                        "It showed a lady fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer.",
                                        "Gregor then turned to look out the window at the dull weather.",
                                        "Drops");
                        java.util.List<String> customerNames = Arrays.asList(
                                        "Nabiha Pham", "Sheena Pearce", "Annika Sanford", "Leja Sloan",
                                        "Marissa Strong", "Gerald Dotson", "Orlando Plummer", "Bear Guest",
                                        "Charly Justice", "Gregory Horn");
                        java.util.List<String> customerAddresses = Arrays.asList(
                                        "8129 Linden St. Roslindale, MA 02131",
                                        "8338 Shipley Court Toms River, NJ 08753",
                                        "8142 Mechanic Lane District Heights, MD 20747",
                                        "8015 La Sierra Ave. Kenosha, WI 53140", "33 Hilldale St. Evans, GA 30809",
                                        "810 Oakwood Street Altoona, PA 16601",
                                        "24 S. Yukon Lane Horn Lake, MS 38637",
                                        "8781 Wintergreen Lane Dundalk, MD 21222",
                                        "90 Delaware Court Gallatin, TN 37066",
                                        "7 Branch Drive Conway, SC 29526");
                        java.util.List<String> customerEmails = Arrays.asList(
                                        "stakasa@att.net", "greear@msn.com", "dialworld@icloud.com", "parasite@me.com",
                                        "techie@att.net", "brainless@icloud.com",
                                        "wikinerd@icloud.com", "jesse@sbcglobal.net", "flaviog@me.com",
                                        "ewaters@icloud.com", "galbra@att.net", "stewwy@me.com");
                        List<String> customerPhone = Arrays.asList(
                                        "(487) 389-3856", "(260) 803-0952", "(832) 829-1280", "(635) 370-8208",
                                        "(342) 637-3526",
                                        "(874) 240-3413", "(796) 330-2425", "(967) 974-5319", "(332) 289-4788",
                                        "(799) 209-3858");
                        Random random = new Random();
                        for (int i = 0; i < itemNames.size(); i++) {
                                Item item = Item.builder()
                                                .name(StringUtils.capitalize(itemNames.get(i)))
                                                .description(itemDescriptionStrings.get(i))
                                                // .quantity(random.nextInt(100))
                                                // .price(random.nextFloat()*100)
                                                // .category(Category.builder().id(random.nextInt(3) + 1).build())
                                                .brand(Brand.builder().id(random.nextInt(4) + 1).build())
                                                .subcategory(Subcategory.builder().id(random.nextInt(3) + 1).build())
                                                .build();
                                itemService.save(item);
                        }
                        for (int i = 0; i < 10; i++) {
                                Customer customer = Customer.builder()
                                                .name(customerNames.get(i))
                                                .address(customerAddresses.get(i))
                                                .phone(customerPhone.get(i))
                                                .email(customerEmails.get(i))
                                                .build();
                                customerService.save(customer);
                        }
                };

        }

        @Bean
        public CommandLineRunner initializeSecurityData(AuthorityService authorityService, RoleService roleService) {

                return args -> {
                        if (!authorityService.findAll().isEmpty() &&
                                        !roleService.findAll().isEmpty()) {
                                return;
                        }
                        Arrays.stream(Permission.values())
                                        .map(permission -> new Authority(permission.getPermission()))
                                        .forEach(authorityService::save);
                        Arrays.stream(UserRole.values())
                                        .map(userRole -> {
                                                System.out.println(userRole);
                                                Role role = new Role();
                                                role.setName("ROLE_" + userRole.name());
                                                userRole.getPermissions()
                                                                .stream()
                                                                .map(Permission::getPermission)
                                                                .map(authorityService::findByName)
                                                                .forEach(role.getAuthorities()::add);
                                                return role;
                                        })
                                        .forEach(roleService::save);
                        roleService.findAll()
                                        .forEach(role -> System.out
                                                        .println(role.getName() + ": " + role.getAuthorities()));
                };

        }

        @Bean
        public CommandLineRunner initializeUsersData(UserService userService, PasswordEncoder passwordEncoder,
                        RoleService roleService) {
                return args -> {
                        if (!userService.findAll().isEmpty() && !roleService.findAll().isEmpty()) {
                                return;
                        }
                        User staffUser = User.builder()
                                        .username("staff")
                                        .password(passwordEncoder.encode("123"))
                                        .isEnabled(true).isAccountNonExpired(true).isCredentialsNonExpired(true)
                                        .isCredentialsNonExpired(true).isAccountNonLocked(true)
                                        .role(roleService.findByName("ROLE_STAFF"))
                                        .profile(
                                                        UserProfile.builder()
                                                                        .email("staff@default.com")
                                                                        .build())
                                        .build();
                        User managerUser = User.builder()
                                        .username("manager")
                                        .password(passwordEncoder.encode("123"))
                                        .isEnabled(true).isAccountNonExpired(true).isCredentialsNonExpired(true)
                                        .isCredentialsNonExpired(true).isAccountNonLocked(true)
                                        .role(roleService.findByName("ROLE_MANAGER"))
                                        .profile(
                                                        UserProfile.builder()
                                                                        .email("manager@default.com")
                                                                        .build())
                                        .build();
                        User itUser = User.builder()
                                        .username("it")
                                        .password(passwordEncoder.encode("1234"))
                                        .isEnabled(true).isAccountNonExpired(true).isCredentialsNonExpired(true)
                                        .isCredentialsNonExpired(true).isAccountNonLocked(true)
                                        .role(roleService.findByName("ROLE_IT_PERSON"))
                                        .profile(
                                                        UserProfile.builder()
                                                                        .email("it@default.com")
                                                                        .build())
                                        .build();
                        userService.saveNewUser(staffUser);
                        userService.saveNewUser(managerUser);
                        userService.saveNewUser(itUser);
                };
        }

}
