package com.light.outside.comes.model;

import java.util.Date;

/**
 * Created by b3st9u on 16/11/5.
 */
public class BanquetRecordViewModel extends BanquetRecordModel {
    private String title;
    private String author_nickname;
    private String author_telephone;
    private String author_address;
    private Date start_time;
    private Date end_time;
    private int table_num;
    private int seat_num;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getTable_num() {
        return table_num;
    }

    @Override
    public void setTable_num(int table_num) {
        this.table_num = table_num;
    }

    @Override
    public int getSeat_num() {
        return seat_num;
    }

    @Override
    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public String getAuthor_telephone() {
        return author_telephone;
    }

    public void setAuthor_telephone(String author_telephone) {
        this.author_telephone = author_telephone;
    }

    public String getAuthor_address() {
        return author_address;
    }

    public void setAuthor_address(String author_address) {
        this.author_address = author_address;
    }
}
