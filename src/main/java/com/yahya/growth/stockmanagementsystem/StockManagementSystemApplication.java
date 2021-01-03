package com.yahya.growth.stockmanagementsystem;

import com.yahya.growth.stockmanagementsystem.model.*;
import com.yahya.growth.stockmanagementsystem.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class StockManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner categoryData(
            CategoryService categoryService, SubcategoryService subcategoryService, BrandService brandService,
            SupplierService supplierService, ItemService itemService) {
        return args -> {
            Category electronicsCategory =
                    Category.builder()
                            .name("Electronics")
                            .description("Electronics Stuff")
                            .build();
            Category computerCategory =
                    Category.builder()
                            .name("Computer")
                            .description("Computer & Accessories")
                            .build();
            Category smartHomeCategory =
                    Category.builder()
                            .name("Smart Home")
                            .description("Smarter House")
                            .build();
            Category softwareCategory =
                    Category.builder()
                    .name("Software")
                    .description("Digital Items")
                    .build();
            electronicsCategory = categoryService.save(electronicsCategory);
            computerCategory = categoryService.save(computerCategory);
            smartHomeCategory = categoryService.save(smartHomeCategory);
            softwareCategory = categoryService.save(softwareCategory);

            Subcategory mobileSubcategory =
                    Subcategory.builder()
                            .name("Mobiles & Accessories")
                            .description("Mobiles & Accessories")
                            .category(electronicsCategory)
                            .build();
            Subcategory tvSubcategory =
                    Subcategory.builder()
                            .name("TV")
                            .description("Televisions")
                            .category(electronicsCategory)
                            .build();
            Subcategory laptopSubcategory =
                    Subcategory.builder()
                            .name("Laptop")
                            .description("Laptops")
                            .category(computerCategory)
                            .build();
            Subcategory alexaSubcategory =
                    Subcategory.builder()
                            .name("Alexa")
                            .description("Alexa the pigeon")
                            .category(smartHomeCategory)
                            .build();
            subcategoryService.save(mobileSubcategory);
            subcategoryService.save(alexaSubcategory);
            subcategoryService.save(laptopSubcategory);
            subcategoryService.save(tvSubcategory);

            Brand appleBrand =
                    Brand.builder()
                            .name("Apple")
                            .description("Expensive Shit")
                            .build();
            Brand samsungBrand =
                    Brand.builder()
                            .name("Samsung Inc.")
                            .description("Slightly Cheaper than Apple")
                            .build();
            Brand lgBrand =
                    Brand.builder()
                    .name("LG")
                    .description("Life is Good")
                    .build();
            Brand amazonBrand =
                    Brand.builder()
                    .name("Amazon Inc.")
                    .description("The biggest")
                    .build();
            Brand microsoftBrand =
                    Brand.builder()
                    .name("Microsoft Inc.")
                    .description("One of tht biggest ones out there")
                    .build();
            brandService.save(lgBrand);
            brandService.save(appleBrand);
            brandService.save(samsungBrand);
            brandService.save(amazonBrand);
            brandService.save(microsoftBrand);

            Supplier vatanSupplier =
                    Supplier.builder()
                            .name("Vatan")
                            .description("Vatan Turkiye")
                            .address("Istanbul, Turkey")
                            .email("vatan@vatan.vt")
                            .phone("+905364859687")
                            .build();
            Supplier mediaSupplier =
                    Supplier.builder()
                            .name("Media Markt")
                            .description("Media Markt Distributer")
                            .address("Merter, Istanbul, Turkey")
                            .email("media@makt.pl")
                            .phone("+906358884512")
                            .build();
            Supplier hepsiSupplier =
                    Supplier.builder()
                            .name("Hepsi")
                            .description("hepsi burada")
                            .address("Bakirkoy, Istanbul, Turkey")
                            .email("hepsi@hepsi.burada")
                            .phone("+903651253698")
                            .build();
            Supplier migrosSupplier =
                    Supplier.builder()
                            .name("Migros")
                            .description("Migros Turkiye")
                            .address("Florya, Istanbul, Turkey")
                            .email("migros@migros.mg")
                            .phone("448")
                            .build();
            supplierService.save(vatanSupplier);
            supplierService.save(mediaSupplier);
            supplierService.save(hepsiSupplier);
            supplierService.save(migrosSupplier);

            List<String> itemNames = Arrays.asList(
                    "boom box", "spring", "hair tie", "floor",  "newspaper", "tissue box", "headphones", "picture frame", "pillow", "shirt"
            );
            List<String> itemDescriptionStrings = Arrays.asList(
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
                    "Drops"
            );
            Random random = new Random();
            for (int i = 0; i < itemNames.size(); i++) {
                Item item =
                        Item.builder()
                                .name(itemNames.get(i))
                                .description(itemDescriptionStrings.get(i))
                                .quantity(random.nextInt())
                                .price(random.nextFloat())
                                .category(Category.builder().id(random.nextInt(3) + 1).build())
                                .brand(Brand.builder().id(random.nextInt(4) + 1).build())
                                .subcategory(Subcategory.builder().id(random.nextInt(3) + 1).build())
                                .supplier(Supplier.builder().id(random.nextInt(3)+1).build())
                                .build();
                itemService.save(item);
            }
        };
    }

}
