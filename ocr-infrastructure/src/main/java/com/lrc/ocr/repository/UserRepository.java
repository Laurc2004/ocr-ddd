package com.lrc.ocr.repository;

import com.lrc.ocr.dao.UserMapper;
import com.lrc.ocr.domain.user.model.entity.UserEntity;
import com.lrc.ocr.domain.user.repository.IUserRepository;
import com.lrc.ocr.po.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserRepository implements IUserRepository {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据openid查询用户
     * @param openid
     * @return 用户信息
     */
    @Override
    public UserEntity queryUserByOpenid(String openid) {
        // 查询数据库
        User user = userMapper.queryUserByOpenid(openid);
        if (ObjectUtils.isEmpty(user)) return null;
        // 返回
        return new UserEntity().setOpenid(user.getOpenid())
                .setId(user.getId())
                .setLines(user.getLines());
    }

    /**
     * 查询用户
     * @param insertUser
     */
    @Override
    public void insert(UserEntity insertUser) {
        User user = new User().setLines(insertUser.getLines())
                .setOpenid(insertUser.getOpenid());
        userMapper.insert(user);
    }

    @Override
    public UserEntity getById(Long id) {
        User user = userMapper.getById(id);

        return new UserEntity(user.getId(), user.getOpenid(), user.getLines());
    }
//    @Override
//    public UserEntity getByUsername(String username) {
//        // 执行查找数据库操作
//        User user = userMapper.getByUsername(username);
//        // 拷贝实体类
//        return new UserEntity(user.getId(), user.getUsername(), user.getPassword());
//    }

}
