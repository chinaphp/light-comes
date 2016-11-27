package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.light.outside.comes.model.PastDetail;
import com.light.outside.comes.model.PastModel;
import com.light.outside.comes.model.PastTotal;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
@Service
public class PastService {

    @Autowired
    private PersistentDao persistentDao;


    public PastModel getPastModelById() {
        return this.persistentDao.getPastById(CONST.PAST_ID);
    }

    /**
     * 保存签到活动信息
     *
     * @param pastModel
     * @return
     */
    public PastModel svePastModel(PastModel pastModel) {
        Preconditions.checkNotNull(pastModel);
        pastModel.setId(CONST.PAST_ID);
        PastModel oldPast = this.persistentDao.getPastById(CONST.PAST_ID);

        if (oldPast != null) {
            //更新
            this.persistentDao.updatePast(pastModel);
        } else {
            //添加
            pastModel.setCreate_time(new Date());
            this.persistentDao.addPast(pastModel);
        }
        return pastModel;
    }


    /**
     * 根据用户的手机号码获取用户的周期的干杯信息
     *
     * @param userModel
     * @return
     */
    public PastTotal getPastTotalByPhone(UserModel userModel) {
        Preconditions.checkNotNull(userModel);
        PastTotal pastTotal = this.persistentDao.getPastTotalByPhone(userModel.getPhone());
        if (pastTotal == null) {
            //加入本周期的相关信息
            pastTotal = new PastTotal();
            pastTotal.setPhone(userModel.getPhone());
            pastTotal.setUid(userModel.getUserid());
            pastTotal.setCycle_drunk(0);
            pastTotal.setCycle_times(0);
            pastTotal.setToday_drunk(0);
            pastTotal.setToday_other_drunk(0);
            pastTotal.setToday_other_times(0);
            pastTotal.setToday_times(0);
            this.persistentDao.addPastTotal(pastTotal);
        }

        //根据信息判断用户当前的签到状态
        PastModel pastModel = this.getPastModelById();
        if (pastModel != null) {
            int times = this.getTodayDrunkTimes(userModel.getPhone());
            pastTotal.setToday_times(times);
            pastTotal.setToday_have_times(pastModel.getPast_times() - times);
            pastTotal.setToday_drunk(pastModel.getTotal_drunk());
        }

        return pastTotal;
    }

    /**
     * 签到并获取最后的信息
     *
     * @param userModel
     * @return
     */
    public PastTotal pastSelf(UserModel userModel) {
        Preconditions.checkNotNull(userModel);
        PastModel pastModel = this.getPastModelById();
        if (pastModel != null) {

            //判断今天次数是否达到
            int times = this.getTodayDrunkTimes(userModel.getPhone());
            if (times < pastModel.getPast_times()) {

                PastDetail pastDetail = new PastDetail();
                pastDetail.setCreate_time(new Date());
                pastDetail.setDrunk_type(CONST.DRUNK_SELF);
                pastDetail.setPhone(userModel.getPhone());
                pastDetail.setUid(userModel.getUserid());

                if (pastModel.getPast_type() == 1) {
                    //固定值
                    pastDetail.setDrunk_num(pastModel.getFix_drunk());
                } else {
                    //随机值
                    Random random = new Random();
                    pastDetail.setDrunk_num(random.nextInt((pastModel.getMax_drunk() - pastModel.getMin_drunk() + 1)) + pastModel.getMin_drunk());
                }
                this.persistentDao.addPastDetail(pastDetail);

                //对PastTotal进行更新
                PastTotal pastTotal = this.persistentDao.getPastTotalByPhone(userModel.getPhone());
                if (pastTotal != null) {
                    pastTotal.setCycle_drunk(pastTotal.getCycle_drunk() + pastDetail.getDrunk_num());
                    pastTotal.setToday_drunk(pastTotal.getToday_drunk() + pastDetail.getDrunk_num());
                    pastTotal.setCycle_times(pastTotal.getCycle_times() + 1);
                    pastTotal.setToday_times(pastTotal.getToday_times() + 1);

                    this.persistentDao.updatePastTotal(pastTotal);
                }
            }
        }
        return this.getPastTotalByPhone(userModel);
    }


    public int getTodayDrunkTimes(String phone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date());

        return this.persistentDao.countPastDetailByPhoneAndTime(phone, time + " 00:00:01", time + " 23:59:59");
    }
}