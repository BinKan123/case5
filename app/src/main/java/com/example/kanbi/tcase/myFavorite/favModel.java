package com.example.kanbi.tcase.myFavorite;

/**
 * Created by kanbi on 16/11/2017.
 */

public class favModel {
        private String currency;
        private Long id;
        private String image;
        private Long price;
        private String title;

        public favModel() {}  // Needed for Firebase

        public favModel(Long id, String title, Long price, String currency, String imageURL) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.currency = currency;
            this.image = imageURL;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
}

