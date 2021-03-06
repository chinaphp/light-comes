package com.light.outside.comes.model;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CouponModel extends BaseModel {

    /**
     * 名称
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 可以使用开始时间
     */
    private Date use_start_time;

    /**
     * 过期时间
     */
    private Date use_end_time;

    /**
     * 生成张数
     */
    private int num;

    /**
     * 劵类型,1全品类，2，商品类别，3，单个商品
     */
    private int ctype;

    /**
     * 商品ID集合
     */
    private long mid;

    /**
     * 金额
     */
    private float price;

    /**
     * 状态
     */
    private int status;

    /**
     * 时间范围
     */
    private String rang_time;

    /**
     * 备注
     */
    private String memo;

    /**
     * 发放张数
     */
    private int sendnum;

    /**
     * 使用张数
     */
    private int usenum;
    /**
     * 使用规则说明
     */
    private String rule;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getSendnum() {
        return sendnum;
    }

    public void setSendnum(int sendnum) {
        this.sendnum = sendnum;
    }

    public int getUsenum() {
        return usenum;
    }

    public void setUsenum(int usenum) {
        this.usenum = usenum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUse_start_time() {
        return use_start_time;
    }

    public void setUse_start_time(Date use_start_time) {
        this.use_start_time = use_start_time;
    }

    public Date getUse_end_time() {
        return use_end_time;
    }

    public void setUse_end_time(Date use_end_time) {
        this.use_end_time = use_end_time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRang_time() {
        return rang_time;
    }

    public void setRang_time(String rang_time) {
        this.rang_time = rang_time;
    }


    public void rangle_time() {
        if (!Strings.isNullOrEmpty(rang_time)) {
            String[] times = rang_time.split("-");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                this.use_start_time = simpleDateFormat.parse(times[0]);
                this.use_end_time = simpleDateFormat.parse(times[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
