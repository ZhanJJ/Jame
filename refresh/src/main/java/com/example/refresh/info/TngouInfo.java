package com.example.refresh.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by James on 2017/1/3.
 */

public class TngouInfo {
    //注解
    @SerializedName("status")
    public boolean status;
    @SerializedName("total")
    public int total;
    @SerializedName("tngou")
    public List<Cook> list;

    public class Cook {

        /**
         * count : 38
         * description : 炸鸡块时油温要高，以达到外焦里嫩的口感，表面金黄后，里面还没熟透可以关成中火继续炸，但时间不要太长，以免影响里面肉质的鲜嫩
         * fcount : 0
         * food : 鸡,琵琶腿,干辣椒,花椒,胡椒粉,酱油,料酒
         * id : 75057
         * images :
         * img : /cook/080316/3d0c6071ce01c9e4969ddd32624821b6.jpg
         * keywords : 可以 辣椒 鸡块 花椒 油温
         * name : 红红火火辣子鸡
         * rcount : 0
         */

        private int count;
        private String description;
        private int fcount;
        private String food;
        private int id;
        private String images;
        private String img;
        private String keywords;
        private String name;
        private int rcount;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }
    }
}
