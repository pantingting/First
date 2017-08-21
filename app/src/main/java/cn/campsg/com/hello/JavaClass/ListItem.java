package cn.campsg.com.hello.JavaClass;

import java.util.Date;

/**
 * Created by C515 on 2017/5/4.
 */
public class ListItem {
    private String type;
    private String category;
    private String money;
    private String description;
    private String date;
    private String place;

    public ListItem(
           String type,String category,String money,String description,String date,String place
          // boolean isShow, boolean isChecked
    ){
        this.type=type;
        this.category=category;
        this.money=money;
        this.description=description;
        this.date=date;

        this.place=place;

    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
