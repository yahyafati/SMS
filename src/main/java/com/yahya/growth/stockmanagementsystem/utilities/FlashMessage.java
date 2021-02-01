package com.yahya.growth.stockmanagementsystem.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashMessage {

    public  enum Type {
        ERROR("error"),
        CONFIRM("confirm"),
        INFO("info"),
        WARNING("warning");

        private final String name;
        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private String message;
    private String gotoURL;
    private Type type;

}
