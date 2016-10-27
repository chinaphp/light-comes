package com.light.outside.comes.mybatis.mapper;

import com.light.outside.comes.model.AuctionModel;
import com.light.outside.comes.model.AuctionRecordsModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by b3st9u on 16/10/18.
 */
public interface AuctionDao {
    @Select("select * from comes_auction where status<>9 and id=#{id}")
    public AuctionModel getAuctionsById(@Param("id") long id);

    /**
     * 插入出价记录
     * @param aid
     * @param price
     * @param uid
     * @param phone
     * @return
     */
    @Insert("insert into comes_auction_records(aid,price,uid,phone) values(#{aid},#{price},#{uid},#{phone})")
    public int addAuctionRecords(@Param("aid") long aid,@Param("price") float price,@Param("uid") long uid,@Param("phone") String phone);

    /**
     * 查询该拍卖纪录
     * @param aid
     * @return
     */
    @Select("select * from comes_auction_records where aid=#{aid} group by uid order by price desc ")
    public List<AuctionRecordsModel> selectAuctionRecordsByAid(@Param("aid") long aid);

}