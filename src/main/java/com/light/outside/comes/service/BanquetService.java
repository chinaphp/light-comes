package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.light.outside.comes.model.*;
import com.light.outside.comes.mybatis.mapper.BanquetDao;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.utils.CONST;
import com.light.outside.comes.utils.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
public class BanquetService {


    @Autowired
    private PersistentDao persistentDao;

    @Autowired
    private BanquetDao banquetDao;

    @Autowired
    private PayService payService;

    private Logger LOG = LoggerFactory.getLogger(BanquetService.class);

    /**
     * 根据状态获取我参与的活动
     *
     * @param status
     * @param pageModel
     * @param userModel
     * @return
     */
    public List<BanquetModel> getBanquetModelByMe(int status, PageModel pageModel, UserModel userModel) {
        Preconditions.checkArgument(status > 0);
        Preconditions.checkNotNull(pageModel);
        Preconditions.checkNotNull(userModel);


        return null;
    }

    /**
     * 保存饭局
     *
     * @param banquetModel
     */
    public void addBanquet(BanquetModel banquetModel) {
        Preconditions.checkNotNull(banquetModel);

        this.persistentDao.addBanquet(banquetModel);
    }


    /**
     * 分页获取饭局
     *
     * @param pageModel
     * @return
     */
    public PageResult<BanquetModel> getBanquets(PageModel pageModel) {
        int total = this.persistentDao.banquetTotal();

        List<BanquetModel> banquetModelList = this.persistentDao.getBanquets(pageModel.getStart(), pageModel.getSize());

        PageResult<BanquetModel> couponModelPageResult = new PageResult<BanquetModel>();
        couponModelPageResult.setData(banquetModelList);
        couponModelPageResult.setPageModel(pageModel);
        couponModelPageResult.setTotal(total);
        return couponModelPageResult;
    }

    /**
     * 分页获取饭局会输出更多的信息，像是否可以成功举办了，当前报名人数等
     *
     * @param pageModel
     * @return
     */
    public PageResult<BanquetModel> getBanquetsMoreInfo(PageModel pageModel) {
        int total = this.persistentDao.banquetTotal();

        List<BanquetModel> banquetModelList = this.persistentDao.getBanquets(pageModel.getStart(), pageModel.getSize());
        if (banquetModelList != null) {
            for (BanquetModel banquetModel : banquetModelList) {
                int enroll_num = this.banquetDao.enrollBanquetTotal(banquetModel.getId());
                banquetModel.setEnroll_num(enroll_num);
                if (enroll_num > banquetModel.getOutnumber()) {
                    banquetModel.setHold(1);
                }
            }
        }

        PageResult<BanquetModel> couponModelPageResult = new PageResult<BanquetModel>();
        couponModelPageResult.setData(banquetModelList);
        couponModelPageResult.setPageModel(pageModel);
        couponModelPageResult.setTotal(total);
        return couponModelPageResult;
    }

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    public BanquetModel getBanquetById(long id) {
        Preconditions.checkArgument(id > 0);
        return this.persistentDao.getBanquetById(id);
    }


    /**
     * 更新Banquet对象
     *
     * @param banquetModel
     */
    public void updateBanquet(BanquetModel banquetModel) {
        Preconditions.checkNotNull(banquetModel);
        if (Strings.isNullOrEmpty(banquetModel.getPhoto()) || CONST.SITE_URL.equalsIgnoreCase(banquetModel.getPhoto().replace("null", ""))) {
            //使用原来的图片
            BanquetModel oldBan = this.getBanquetById(banquetModel.getId());
            banquetModel.setPhoto(oldBan.getPhoto());
        }
        this.persistentDao.updateBanquet(banquetModel);
    }


    /**
     * 判断是否参与过该饭局
     *
     * @param userModel
     * @param aid       活动ID
     * @return
     */
    public boolean isJoinBanquet(UserModel userModel, long aid) {
        Preconditions.checkNotNull(userModel);
        Preconditions.checkArgument(aid > 0);
        boolean res = false;
        BanquetRecordModel banquetRecordModel = this.banquetDao.getBanquetRecordByAidAndPhone(aid, userModel.getPhone());
        if (banquetRecordModel != null && banquetRecordModel.getStatus() == CONST.ORDER_PAY) {
            res = true;
        }
        return res;
    }

    public void deleteBanquet(long id) {
        Preconditions.checkArgument(id > 0);
        BanquetModel banquetModel = this.getBanquetById(id);
        if (banquetModel != null) {
            banquetModel.setStatus(CONST.RAFFLE_STATUS_DELETE);
            this.updateBanquet(banquetModel);
        }
    }


    /**
     * 支付约饭
     *
     * @param aid
     * @param userModel
     * @return
     */
    public OrderModel payBanquet(long aid, UserModel userModel) {
        Preconditions.checkArgument(aid > 0);
        Preconditions.checkNotNull(userModel);

        OrderModel orderModel = null;
        try {
            BanquetModel banquetModel = this.getBanquetById(aid);
            if (banquetModel != null) {
                orderModel = new OrderModel();
                orderModel.setAmount(banquetModel.getAmount());
                orderModel.setAname(banquetModel.getTitle());
                orderModel.setAtype(CONST.FOCUS_BANQUET);
                orderModel.setCreatetime(new Date());
                orderModel.setPhone(userModel.getPhone());
                orderModel.setUid(userModel.getId());
                orderModel.setStatus(CONST.ORDER_PAY);
                orderModel.setPtype(CONST.PAY_WEIXIN);
                orderModel.setOrderNo(OrderUtil.getOrderNo());
                orderModel.setPaytime(new Date());
                orderModel.setAid(aid);
                long oid = this.payService.createOrder(orderModel);
                if (oid > 0) {
                    orderModel.setId(oid);
                }


                //写入记录
                BanquetRecordModel banquetRecordModel = this.banquetDao.getBanquetRecordByAidAndPhone(aid, userModel.getPhone());
                if (banquetRecordModel == null) {
                    banquetRecordModel = new BanquetRecordModel();
                    banquetRecordModel.setAid(aid);
                    banquetRecordModel.setTitle(banquetModel.getTitle());
                    banquetRecordModel.setUid(userModel.getId());
                    banquetRecordModel.setPhone(userModel.getPhone());
                    banquetRecordModel.setAmount(banquetModel.getAmount());
                    banquetRecordModel.setOrderNo(orderModel.getOrderNo());
                    banquetRecordModel.setCreatetime(new Date());
                } else {
                    //更新当前记录的支付号
                    banquetRecordModel.setOrderNo(orderModel.getOrderNo());
                }

                if (orderModel.getStatus() == CONST.ORDER_PAY) {
                    banquetRecordModel.setStatus(CONST.ORDER_PAY);
                } else {
                    banquetRecordModel.setStatus(CONST.ORDER_CREATE);
                }

                if (banquetRecordModel.getId() > 0) {
                    //更新
                    this.banquetDao.updateBanquetRecordModel(banquetRecordModel);
                } else {
                    //创建
                    this.banquetDao.addBanquetRecordModel(banquetRecordModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderModel;
    }

    /**
     * 清除过期约饭
     */
    public void clearBanquet() {
        List<BanquetModel> banquetModels = this.persistentDao.getBanquets(1, Integer.MAX_VALUE);
        if (banquetModels != null) {
            for (BanquetModel banquetModel : banquetModels) {
                if (banquetModel.getEnd_time().getTime() <= System.currentTimeMillis() && banquetModel.getStatus() != CONST.RAFFLE_STATUS_OVER) {
                    banquetModel.setStatus(CONST.RAFFLE_STATUS_OVER);
                    LOG.info("over banquet id:" + banquetModel.getId() + " name:" + banquetModel.getTitle());
                    this.updateBanquet(banquetModel);
                }
            }
        }
    }

    /**
     * 分页查询约饭
     *
     * @param uid
     * @param status
     * @param pageModel
     * @return
     */
    public PageResult<BanquetRecordModel> getBanquetRecordPage(long uid, int status, PageModel pageModel) {
        PageResult<BanquetRecordModel> banquetRecordModelPageResult = new PageResult<BanquetRecordModel>();
        List<BanquetRecordModel> banquetRecordModels;
        if (status > 0)
            banquetRecordModels = banquetDao.getBanquetRecordPageByUidAndStatus(uid, status, pageModel.getStart(), pageModel.getSize());
        else
            banquetRecordModels = banquetDao.getBanquetRecordPageByUid(uid, pageModel.getStart(), pageModel.getSize());
        banquetRecordModelPageResult.setData(banquetRecordModels);
        banquetRecordModelPageResult.setPageModel(pageModel);
        return banquetRecordModelPageResult;
    }


}
